// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package com.cloud.kubernetes.cluster.actionworkers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.cloud.kubernetes.cluster.KubernetesClusterVmMapVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import com.cloud.hypervisor.Hypervisor;
import com.cloud.kubernetes.cluster.KubernetesCluster;
import com.cloud.kubernetes.cluster.KubernetesClusterManagerImpl;
import com.cloud.kubernetes.cluster.KubernetesClusterService;
import com.cloud.kubernetes.cluster.KubernetesClusterVO;
import com.cloud.kubernetes.cluster.utils.KubernetesClusterUtil;
import com.cloud.kubernetes.version.KubernetesSupportedVersion;
import com.cloud.kubernetes.version.KubernetesVersionManagerImpl;
import com.cloud.uservm.UserVm;
import com.cloud.utils.Pair;
import com.cloud.utils.exception.CloudRuntimeException;
import com.cloud.utils.ssh.SshHelper;

public class KubernetesClusterUpgradeWorker extends KubernetesClusterActionWorker {

    protected List<UserVm> clusterVMs = new ArrayList<>();
    private KubernetesSupportedVersion upgradeVersion;
    private final String upgradeScriptFilename = "upgrade-kubernetes.sh";
    private File upgradeScriptFile;
    private long upgradeTimeoutTime;

    public KubernetesClusterUpgradeWorker(final KubernetesCluster kubernetesCluster,
                                          final KubernetesSupportedVersion upgradeVersion,
                                          final KubernetesClusterManagerImpl clusterManager,
                                          final String[] keys) {
        super(kubernetesCluster, clusterManager);
        this.upgradeVersion = upgradeVersion;
        this.keys = keys;
    }

    protected void retrieveScriptFiles() {
        super.retrieveScriptFiles();
        upgradeScriptFile = retrieveScriptFile(upgradeScriptFilename);
    }

    private Pair<Boolean, String> runInstallScriptOnVM(final UserVm vm, final int index) throws Exception {
        int nodeSshPort = sshPort == 22 ? sshPort : sshPort + index;
        String nodeAddress = (index > 0 && sshPort == 22) ? vm.getPrivateIpAddress() : publicIpAddress;
        SshHelper.scpTo(nodeAddress, nodeSshPort, getControlNodeLoginUser(), sshKeyFile, null,
                "~/", upgradeScriptFile.getAbsolutePath(), "0755");
        String cmdStr = String.format("sudo ./%s %s %s %s %s %s",
                upgradeScriptFile.getName(),
                upgradeVersion.getSemanticVersion(),
                index == 0 ? "true" : "false",
                KubernetesVersionManagerImpl.compareSemanticVersions(upgradeVersion.getSemanticVersion(), "1.15.0") < 0 ? "true" : "false",
                Hypervisor.HypervisorType.VMware.equals(vm.getHypervisorType()), Objects.isNull(kubernetesCluster.getCniConfigId()));
        return SshHelper.sshExecute(nodeAddress, nodeSshPort, getControlNodeLoginUser(), sshKeyFile, null,
                cmdStr,
                10000, 10000, 10 * 60 * 1000);
    }

    private void upgradeKubernetesClusterNodes() {
        for (int i = 0; i < clusterVMs.size(); ++i) {
            UserVm vm = clusterVMs.get(i);
            String hostName = vm.getHostName();
            if (StringUtils.isNotEmpty(hostName)) {
                hostName = hostName.toLowerCase();
            }
            Pair<Boolean, String> result;
            if (logger.isInfoEnabled()) {
                logger.info("Upgrading node on VM {} in Kubernetes cluster {} with Kubernetes version {}", vm, kubernetesCluster, upgradeVersion);
            }
            String errorMessage = String.format("Failed to upgrade Kubernetes cluster : %s, unable to drain Kubernetes node on VM : %s", kubernetesCluster.getName(), vm.getDisplayName());
            for (int retry = KubernetesClusterService.KubernetesClusterUpgradeRetries.value(); retry >= 0; retry--) {
                try {
                    result = SshHelper.sshExecute(publicIpAddress, sshPort, getControlNodeLoginUser(), sshKeyFile, null,
                            String.format("sudo /opt/bin/kubectl drain %s --ignore-daemonsets --delete-emptydir-data", hostName),
                            10000, 10000, 60000);
                    if (result.first()) {
                        break;
                    }
                    if (retry > 0) {
                        logger.error(String.format("%s, retries left: %s", errorMessage, retry));
                    } else {
                        logTransitStateDetachIsoAndThrow(Level.ERROR, errorMessage, kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
                    }
                } catch (Exception e) {
                    if (retry > 0) {
                        logger.error(String.format("%s due to %s, retries left: %s", errorMessage, e, retry));
                    } else {
                        logTransitStateDetachIsoAndThrow(Level.ERROR, errorMessage, kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, e);
                    }
                }
            }
            if (System.currentTimeMillis() > upgradeTimeoutTime) {
                logTransitStateDetachIsoAndThrow(Level.ERROR, String.format("Failed to upgrade Kubernetes cluster : %s, upgrade action timed out", kubernetesCluster.getName()), kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
            }
            errorMessage = String.format("Failed to upgrade Kubernetes cluster : %s, unable to upgrade Kubernetes node on VM : %s", kubernetesCluster.getName(), vm.getDisplayName());
            for (int retry = KubernetesClusterService.KubernetesClusterUpgradeRetries.value(); retry >= 0; retry--) {
                try {
                    deployProvider();
                    result = runInstallScriptOnVM(vm, i);
                    if (result.first()) {
                        break;
                    }
                    if (retry > 0) {
                        logger.error(String.format("%s, retries left: %s", errorMessage, retry));
                    } else {
                        logTransitStateDetachIsoAndThrow(Level.ERROR, errorMessage, kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
                    }
                } catch (Exception e) {
                    if (retry > 0) {
                        logger.error(String.format("%s due to %s, retries left: %s", errorMessage, e, retry));
                    } else {
                        logTransitStateDetachIsoAndThrow(Level.ERROR, errorMessage, kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, e);
                    }
                }
            }
            if (System.currentTimeMillis() > upgradeTimeoutTime) {
                logTransitStateDetachIsoAndThrow(Level.ERROR, String.format("Failed to upgrade Kubernetes cluster : %s, upgrade action timed out", kubernetesCluster.getName()), kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
            }
            if (!KubernetesClusterUtil.uncordonKubernetesClusterNode(kubernetesCluster, publicIpAddress, sshPort, getControlNodeLoginUser(), getManagementServerSshPublicKeyFile(), vm, upgradeTimeoutTime, 15000)) {
                logTransitStateDetachIsoAndThrow(Level.ERROR, String.format("Failed to upgrade Kubernetes cluster : %s, unable to uncordon Kubernetes node on VM : %s", kubernetesCluster.getName(), vm.getDisplayName()), kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
            }
            if (i == 0) { // Wait for control node to get in Ready state
                if (!KubernetesClusterUtil.isKubernetesClusterNodeReady(kubernetesCluster, publicIpAddress, sshPort, getControlNodeLoginUser(), getManagementServerSshPublicKeyFile(), hostName, upgradeTimeoutTime, 15000)) {
                    logTransitStateDetachIsoAndThrow(Level.ERROR, String.format("Failed to upgrade Kubernetes cluster : %s, unable to get control Kubernetes node on VM : %s in ready state", kubernetesCluster.getName(), vm.getDisplayName()), kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
                }
            }
            if (!KubernetesClusterUtil.clusterNodeVersionMatches(upgradeVersion.getSemanticVersion(), publicIpAddress, sshPort, getControlNodeLoginUser(), getManagementServerSshPublicKeyFile(), hostName, upgradeTimeoutTime, 15000, vm.getId(), kubernetesClusterVmMapDao)) {
                logTransitStateDetachIsoAndThrow(Level.ERROR, String.format("Failed to upgrade Kubernetes cluster : %s, unable to get Kubernetes node on VM : %s upgraded to version %s", kubernetesCluster.getName(), vm.getDisplayName(), upgradeVersion.getSemanticVersion()), kubernetesCluster, clusterVMs, KubernetesCluster.Event.OperationFailed, null);
            }
            if (logger.isInfoEnabled()) {
                logger.info("Successfully upgraded node on VM {} in Kubernetes cluster {} with Kubernetes version {}", vm, kubernetesCluster, upgradeVersion);
            }
        }
    }

    public boolean upgradeCluster() throws CloudRuntimeException {
        init();
        if (logger.isInfoEnabled()) {
            logger.info("Upgrading Kubernetes cluster: {}", kubernetesCluster);
        }
        upgradeTimeoutTime = System.currentTimeMillis() + KubernetesClusterService.KubernetesClusterUpgradeTimeout.value() * 1000;
        Pair<String, Integer> publicIpSshPort = getKubernetesClusterServerIpSshPort(null);
        publicIpAddress = publicIpSshPort.first();
        sshPort = publicIpSshPort.second();
        if (StringUtils.isEmpty(publicIpAddress)) {
            logAndThrow(Level.ERROR, String.format("Upgrade failed for Kubernetes cluster: %s, unable to retrieve associated public IP", kubernetesCluster));
        }
        clusterVMs = getKubernetesClusterVMs();
        if (CollectionUtils.isEmpty(clusterVMs)) {
            logAndThrow(Level.ERROR, String.format("Upgrade failed for Kubernetes cluster: %s, unable to retrieve VMs for cluster", kubernetesCluster));
        }
        filterOutManualUpgradeNodesFromClusterUpgrade();
        retrieveScriptFiles();
        stateTransitTo(kubernetesCluster.getId(), KubernetesCluster.Event.UpgradeRequested);
        attachIsoKubernetesVMs(clusterVMs, upgradeVersion);
        upgradeKubernetesClusterNodes();
        detachIsoKubernetesVMs(clusterVMs);
        KubernetesClusterVO kubernetesClusterVO = kubernetesClusterDao.findById(kubernetesCluster.getId());
        kubernetesClusterVO.setKubernetesVersionId(upgradeVersion.getId());
        boolean updated = kubernetesClusterDao.update(kubernetesCluster.getId(), kubernetesClusterVO);
        if (!updated) {
            stateTransitTo(kubernetesCluster.getId(), KubernetesCluster.Event.OperationFailed);
        } else {
            stateTransitTo(kubernetesCluster.getId(), KubernetesCluster.Event.OperationSucceeded);
        }
        return updated;
    }

    protected void filterOutManualUpgradeNodesFromClusterUpgrade() {
        if (CollectionUtils.isEmpty(clusterVMs)) {
            return;
        }
        clusterVMs = clusterVMs.stream().filter(x -> {
            KubernetesClusterVmMapVO mapVO = kubernetesClusterVmMapDao.getClusterMapFromVmId(x.getId());
            return mapVO != null && !mapVO.isManualUpgrade();
        }).collect(Collectors.toList());
    }
}
