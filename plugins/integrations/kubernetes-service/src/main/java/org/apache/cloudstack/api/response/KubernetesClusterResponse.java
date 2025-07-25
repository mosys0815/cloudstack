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
package org.apache.cloudstack.api.response;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.BaseResponseWithAnnotations;
import org.apache.cloudstack.api.EntityReference;

import com.cloud.kubernetes.cluster.KubernetesCluster;
import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
@EntityReference(value = {KubernetesCluster.class})
public class KubernetesClusterResponse extends BaseResponseWithAnnotations implements ControlledEntityResponse {
    @SerializedName(ApiConstants.ID)
    @Param(description = "the id of the Kubernetes cluster")
    private String id;

    @SerializedName(ApiConstants.NAME)
    @Param(description = "the name of the Kubernetes cluster")
    private String name;

    @SerializedName(ApiConstants.DESCRIPTION)
    @Param(description = "the description of the Kubernetes cluster")
    private String description;

    @SerializedName(ApiConstants.ZONE_ID)
    @Param(description = "the name of the zone of the Kubernetes cluster")
    private String zoneId;

    @SerializedName(ApiConstants.ZONE_NAME)
    @Param(description = "the name of the zone of the Kubernetes cluster")
    private String zoneName;

    @SerializedName(ApiConstants.SERVICE_OFFERING_ID)
    @Param(description = "the ID of the service offering of the Kubernetes cluster")
    private String serviceOfferingId;

    @SerializedName("serviceofferingname")
    @Param(description = "the name of the service offering of the Kubernetes cluster")
    private String serviceOfferingName;

    @SerializedName(ApiConstants.WORKER_SERVICE_OFFERING_ID)
    @Param(description = "the ID of the service offering of the worker nodes on the Kubernetes cluster")
    private String workerOfferingId;

    @SerializedName(ApiConstants.WORKER_SERVICE_OFFERING_NAME)
    @Param(description = "the name of the service offering of the worker nodes on the Kubernetes cluster")
    private String workerOfferingName;

    @SerializedName(ApiConstants.CONTROL_SERVICE_OFFERING_ID)
    @Param(description = "the ID of the service offering of the control nodes on the Kubernetes cluster")
    private String controlOfferingId;

    @SerializedName(ApiConstants.CONTROL_SERVICE_OFFERING_NAME)
    @Param(description = "the name of the service offering of the control nodes on the Kubernetes cluster")
    private String controlOfferingName;

    @SerializedName(ApiConstants.ETCD_SERVICE_OFFERING_ID)
    @Param(description = "the ID of the service offering of the etcd nodes on the Kubernetes cluster")
    private String etcdOfferingId;

    @SerializedName(ApiConstants.ETCD_SERVICE_OFFERING_NAME)
    @Param(description = "the name of the service offering of the etcd nodes on the Kubernetes cluster")
    private String etcdOfferingName;

    @SerializedName(ApiConstants.ETCD_NODES)
    @Param(description = "the number of the etcd nodes on the Kubernetes cluster")
    private Long etcdNodes;

    @SerializedName(ApiConstants.TEMPLATE_ID)
    @Param(description = "the ID of the template of the Kubernetes cluster")
    private String templateId;

    @SerializedName(ApiConstants.NETWORK_ID)
    @Param(description = "the ID of the network of the Kubernetes cluster")
    private String networkId;

    @SerializedName(ApiConstants.ASSOCIATED_NETWORK_NAME)
    @Param(description = "the name of the network of the Kubernetes cluster")
    private String associatedNetworkName;

    @SerializedName(ApiConstants.KUBERNETES_VERSION_ID)
    @Param(description = "the ID of the Kubernetes version for the Kubernetes cluster")
    private String kubernetesVersionId;

    @SerializedName(ApiConstants.KUBERNETES_VERSION_NAME)
    @Param(description = "the name of the Kubernetes version for the Kubernetes cluster")
    private String kubernetesVersionName;

    @SerializedName(ApiConstants.ACCOUNT)
    @Param(description = "the account associated with the Kubernetes cluster")
    private String accountName;

    @SerializedName(ApiConstants.PROJECT_ID)
    @Param(description = "the project id of the Kubernetes cluster")
    private String projectId;

    @SerializedName(ApiConstants.PROJECT)
    @Param(description = "the project name of the Kubernetes cluster")
    private String projectName;

    @SerializedName(ApiConstants.DOMAIN_ID)
    @Param(description = "the ID of the domain in which the Kubernetes cluster exists")
    private String domainId;

    @SerializedName(ApiConstants.DOMAIN)
    @Param(description = "the name of the domain in which the Kubernetes cluster exists")
    private String domainName;

    @SerializedName(ApiConstants.DOMAIN_PATH)
    @Param(description = "path of the domain to which the Kubernetes cluster belongs", since = "4.19.2.0")
    private String domainPath;

    @SerializedName(ApiConstants.SSH_KEYPAIR)
    @Param(description = "keypair details")
    private String keypair;

    @SerializedName(ApiConstants.CNI_CONFIG_ID)
    @Param(description = "ID of CNI Configuration associated with the cluster")
    private String cniConfigId;

    @SerializedName(ApiConstants.CNI_CONFIG_NAME)
    @Param(description = "Name of CNI Configuration associated with the cluster")
    private String cniConfigName;

    @Deprecated(since = "4.16")
    @SerializedName(ApiConstants.MASTER_NODES)
    @Param(description = "the master nodes count for the Kubernetes cluster. This parameter is deprecated, please use 'controlnodes' parameter.")
    private Long masterNodes;

    @SerializedName(ApiConstants.CONTROL_NODES)
    @Param(description = "the control nodes count for the Kubernetes cluster")
    private Long controlNodes;

    @SerializedName(ApiConstants.SIZE)
    @Param(description = "the size (worker nodes count) of the Kubernetes cluster")
    private Long clusterSize;

    @SerializedName(ApiConstants.STATE)
    @Param(description = "the state of the Kubernetes cluster")
    private String state;

    @SerializedName(ApiConstants.CPU_NUMBER)
    @Param(description = "the cpu cores of the Kubernetes cluster")
    private String cores;

    @SerializedName(ApiConstants.MEMORY)
    @Param(description = "the memory the Kubernetes cluster")
    private String memory;

    @SerializedName(ApiConstants.END_POINT)
    @Param(description = "URL end point for the Kubernetes cluster")
    private String endpoint;

    @SerializedName(ApiConstants.CONSOLE_END_POINT)
    @Param(description = "URL end point for the Kubernetes cluster dashboard UI")
    private String consoleEndpoint;

    @SerializedName(ApiConstants.VIRTUAL_MACHINES)
    @Param(description = "the list of virtualmachine associated with this Kubernetes cluster")
    private List<KubernetesUserVmResponse> virtualMachines;

    @SerializedName(ApiConstants.IP_ADDRESS)
    @Param(description = "Public IP Address of the cluster")
    private String ipAddress;

    @SerializedName(ApiConstants.IP_ADDRESS_ID)
    @Param(description = "Public IP Address ID of the cluster")
    private String ipAddressId;

    @SerializedName(ApiConstants.ETCD_IPS)
    @Param(description = "Public IP Addresses of the etcd nodes")
    private Map<String, String> etcdIps;

    @SerializedName(ApiConstants.AUTOSCALING_ENABLED)
    @Param(description = "Whether autoscaling is enabled for the cluster")
    private boolean isAutoscalingEnabled;

    @SerializedName(ApiConstants.MIN_SIZE)
    @Param(description = "Minimum size of the cluster")
    private Long minSize;

    @SerializedName(ApiConstants.MAX_SIZE)
    @Param(description = "Maximum size of the cluster")
    private Long maxSize;

    @SerializedName(ApiConstants.CLUSTER_TYPE)
    @Param(description = "the type of the cluster")
    private KubernetesCluster.ClusterType clusterType;

    @SerializedName(ApiConstants.CREATED)
    @Param(description = "the date when this Kubernetes cluster was created")
    private Date created;

    public KubernetesClusterResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getServiceOfferingId() {
        return serviceOfferingId;
    }

    public void setServiceOfferingId(String serviceOfferingId) {
        this.serviceOfferingId = serviceOfferingId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getAssociatedNetworkName() {
        return associatedNetworkName;
    }

    public void setAssociatedNetworkName(String associatedNetworkName) {
        this.associatedNetworkName = associatedNetworkName;
    }

    public String getKubernetesVersionId() {
        return kubernetesVersionId;
    }

    public void setKubernetesVersionId(String kubernetesVersionId) {
        this.kubernetesVersionId = kubernetesVersionId;
    }

    public String getKubernetesVersionName() {
        return kubernetesVersionName;
    }

    public void setKubernetesVersionName(String kubernetesVersionName) {
        this.kubernetesVersionName = kubernetesVersionName;
    }

    public String getProjectId() {
        return projectId;
    }

    @Override
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public void setDomainId(String domainId) {
        this.domainId = domainId;
    }

    @Override
    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Override
    public void setDomainPath(String domainPath) {
        this.domainPath = domainPath;
    }
    public String getKeypair() {
        return keypair;
    }

    public void setKeypair(String keypair) {
        this.keypair = keypair;
    }

    public Long getMasterNodes() {
        return masterNodes;
    }

    public void setMasterNodes(Long masterNodes) {
        this.masterNodes = masterNodes;
    }

    public Long getControlNodes() {
        return controlNodes;
    }

    public void setControlNodes(Long controlNodes) {
        this.controlNodes = controlNodes;
    }

    public Long getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(Long clusterSize) {
        this.clusterSize = clusterSize;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceOfferingName() {
        return serviceOfferingName;
    }

    public void setServiceOfferingName(String serviceOfferingName) {
        this.serviceOfferingName = serviceOfferingName;
    }

    public String getWorkerOfferingId() {
        return workerOfferingId;
    }

    public void setWorkerOfferingId(String workerOfferingId) {
        this.workerOfferingId = workerOfferingId;
    }

    public String getWorkerOfferingName() {
        return workerOfferingName;
    }

    public void setWorkerOfferingName(String workerOfferingName) {
        this.workerOfferingName = workerOfferingName;
    }

    public String getControlOfferingId() {
        return controlOfferingId;
    }

    public void setControlOfferingId(String controlOfferingId) {
        this.controlOfferingId = controlOfferingId;
    }

    public String getControlOfferingName() {
        return controlOfferingName;
    }

    public void setControlOfferingName(String controlOfferingName) {
        this.controlOfferingName = controlOfferingName;
    }

    public String getEtcdOfferingId() {
        return etcdOfferingId;
    }

    public void setEtcdOfferingId(String etcdOfferingId) {
        this.etcdOfferingId = etcdOfferingId;
    }

    public String getEtcdOfferingName() {
        return etcdOfferingName;
    }

    public void setEtcdOfferingName(String etcdOfferingName) {
        this.etcdOfferingName = etcdOfferingName;
    }

    public Long getEtcdNodes() {
        return etcdNodes;
    }

    public void setEtcdNodes(Long etcdNodes) {
        this.etcdNodes = etcdNodes;
    }

    public void setVirtualMachines(List<KubernetesUserVmResponse> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }

    public List<KubernetesUserVmResponse> getVirtualMachines() {
        return virtualMachines;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setIpAddressId(String ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    public void setEtcdIps(Map<String, String> etcdIps) {
        this.etcdIps = etcdIps;
    }

    public void setAutoscalingEnabled(boolean isAutoscalingEnabled) {
        this.isAutoscalingEnabled = isAutoscalingEnabled;
    }

    public void setMinSize(Long minSize) {
        this.minSize = minSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public KubernetesCluster.ClusterType getClusterType() {
        return clusterType;
    }

    public void setClusterType(KubernetesCluster.ClusterType clusterType) {
        this.clusterType = clusterType;
    }

    public void setCniConfigId(String cniConfigId) {
        this.cniConfigId = cniConfigId;
    }

    public void setCniConfigName(String cniConfigName) {
        this.cniConfigName = cniConfigName;
    }
}
