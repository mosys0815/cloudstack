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
package com.cloud.kubernetes.cluster;

import java.util.Date;
import java.util.UUID;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import com.cloud.utils.db.GenericDao;
import org.apache.cloudstack.utils.reflectiontostringbuilderutils.ReflectionToStringBuilderUtils;

@Entity
@Table(name = "kubernetes_cluster")
public class KubernetesClusterVO implements KubernetesCluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "zone_id")
    private long zoneId;

    @Column(name = "kubernetes_version_id")
    private Long kubernetesVersionId;

    @Column(name = "service_offering_id")
    private Long serviceOfferingId;

    @Column(name = "template_id")
    private Long templateId;

    @Column(name = "network_id")
    private Long networkId;

    @Column(name = "domain_id")
    private long domainId;

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "control_node_count")
    private long controlNodeCount;

    @Column(name = "node_count")
    private long nodeCount;

    @Column(name = "cores")
    private long cores;

    @Column(name = "memory")
    private long memory;

    @Column(name = "node_root_disk_size")
    private long nodeRootDiskSize;

    @Column(name = "state")
    private State  state;

    @Column(name = "key_pair")
    private String keyPair;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "autoscaling_enabled")
    private boolean autoscalingEnabled;

    @Column(name = "minsize")
    private Long minSize;

    @Column(name = "maxsize")
    private Long maxSize;

    @Column(name = GenericDao.CREATED_COLUMN)
    private Date created;

    @Column(name = GenericDao.REMOVED_COLUMN)
    private Date removed;

    @Column(name = "gc")
    private boolean checkForGc;

    @Column(name = "security_group_id")
    private Long securityGroupId;

    @Column(name = "cluster_type")
    private ClusterType clusterType;

    @Column(name = "control_node_service_offering_id")
    private Long controlNodeServiceOfferingId;

    @Column(name = "worker_node_service_offering_id")
    private Long workerNodeServiceOfferingId;

    @Column(name = "etcd_node_service_offering_id")
    private Long etcdNodeServiceOfferingId;

    @Column(name = "etcd_node_count")
    private Long etcdNodeCount;

    @Column(name = "control_node_template_id")
    private Long controlNodeTemplateId;

    @Column(name = "worker_node_template_id")
    private Long workerNodeTemplateId;

    @Column(name = "etcd_node_template_id")
    private Long etcdNodeTemplateId;

    @Column(name = "cni_config_id", nullable = true)
    private Long cniConfigId = null;

    @Column(name = "cni_config_details", updatable = true, length = 4096)
    private String cniConfigDetails;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public Long getKubernetesVersionId() {
        return kubernetesVersionId;
    }

    public void setKubernetesVersionId(long kubernetesVersionId) {
        this.kubernetesVersionId = kubernetesVersionId;
    }

    @Override
    public Long getServiceOfferingId() {
        return serviceOfferingId;
    }

    public void setServiceOfferingId(long serviceOfferingId) {
        this.serviceOfferingId = serviceOfferingId;
    }

    @Override
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    @Override
    public Long getNetworkId() {
        return networkId;
    }

    public void setNetworkId(long networkId) {
        this.networkId = networkId;
    }

    @Override
    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    @Override
    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public long getControlNodeCount() {
        return controlNodeCount;
    }

    public void setControlNodeCount(long controlNodeCount) {
        this.controlNodeCount = controlNodeCount;
    }

    @Override
    public long getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(long nodeCount) {
        this.nodeCount = nodeCount;
    }

    @Override
    public long getTotalNodeCount() {
        return this.controlNodeCount + this.nodeCount + this.getEtcdNodeCount();
    }

    @Override
    public long getCores() {
        return cores;
    }

    public void setCores(long cores) {
        this.cores = cores;
    }

    @Override
    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }

    @Override
    public long getNodeRootDiskSize() {
        return nodeRootDiskSize;
    }

    public void setNodeRootDiskSize(long nodeRootDiskSize) {
        this.nodeRootDiskSize = nodeRootDiskSize;
    }

    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(String keyPair) {
        this.keyPair = keyPair;
    }

    @Override
    public boolean isDisplay() {
        return true;
    }


    public Date getRemoved() {
        if (removed == null)
            return null;
        return new Date(removed.getTime());
    }

    @Override
    public boolean isCheckForGc() {
        return checkForGc;
    }

    public void setCheckForGc(boolean check) {
        checkForGc = check;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public boolean getAutoscalingEnabled() {
        return autoscalingEnabled;
    }

    public void setAutoscalingEnabled(boolean enabled) {
        this.autoscalingEnabled = enabled;
    }

    @Override
    public Long getMinSize() {
        return minSize;
    }

    public void setMinSize(Long minSize) {
        this.minSize = minSize;
    }

    @Override
    public Long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Long maxSize) {
        this.maxSize = maxSize;
    }

    public void setSecurityGroupId(Long securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public Long getSecurityGroupId() {
        return securityGroupId;
    }

    public ClusterType getClusterType() {
        return clusterType;
    }

    public void setClusterType(ClusterType clusterType) {
        this.clusterType = clusterType;
    }

    public KubernetesClusterVO() {
        this.uuid = UUID.randomUUID().toString();
    }

    public KubernetesClusterVO(String name, String description, long zoneId, Long kubernetesVersionId, Long serviceOfferingId, Long templateId,
                               Long networkId, long domainId, long accountId, long controlNodeCount, long nodeCount, State state,
                               String keyPair, long cores, long memory, Long nodeRootDiskSize, String endpoint, ClusterType clusterType) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.zoneId = zoneId;
        this.kubernetesVersionId = kubernetesVersionId;
        this.serviceOfferingId = serviceOfferingId;
        this.templateId = templateId;
        this.networkId = networkId;
        this.domainId = domainId;
        this.accountId = accountId;
        this.controlNodeCount = controlNodeCount;
        this.nodeCount = nodeCount;
        this.state = state;
        this.keyPair = keyPair;
        this.cores = cores;
        this.memory = memory;
        if (nodeRootDiskSize != null && nodeRootDiskSize > 0) {
            this.nodeRootDiskSize = nodeRootDiskSize;
        }
        this.endpoint = endpoint;
        this.clusterType = clusterType;
        this.checkForGc = false;
    }

    public KubernetesClusterVO(String name, String description, long zoneId, long kubernetesVersionId, long serviceOfferingId, long templateId,
        long networkId, long domainId, long accountId, long controlNodeCount, long nodeCount, State state, String keyPair, long cores,
        long memory, Long nodeRootDiskSize, String endpoint, ClusterType clusterType, boolean autoscalingEnabled, Long minSize, Long maxSize) {
        this(name, description, zoneId, kubernetesVersionId, serviceOfferingId, templateId, networkId, domainId, accountId, controlNodeCount,
            nodeCount, state, keyPair, cores, memory, nodeRootDiskSize, endpoint, clusterType);
        this.autoscalingEnabled = autoscalingEnabled;
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    @Override
    public String toString() {
        return String.format("KubernetesCluster %s",
                ReflectionToStringBuilderUtils.reflectOnlySelectedFields(
                        this, "id", "uuid", "name"));
    }

    @Override
    public Class<?> getEntityType() {
        return KubernetesCluster.class;
    }

    public Long getControlNodeServiceOfferingId() {
        return controlNodeServiceOfferingId;
    }

    public void setControlNodeServiceOfferingId(Long controlNodeServiceOfferingId) {
        this.controlNodeServiceOfferingId = controlNodeServiceOfferingId;
    }

    public Long getWorkerNodeServiceOfferingId() {
        return workerNodeServiceOfferingId;
    }

    public void setWorkerNodeServiceOfferingId(Long workerNodeServiceOfferingId) {
        this.workerNodeServiceOfferingId = workerNodeServiceOfferingId;
    }

    public Long getEtcdNodeServiceOfferingId() {
        return etcdNodeServiceOfferingId;
    }

    public void setEtcdNodeServiceOfferingId(Long etcdNodeServiceOfferingId) {
        this.etcdNodeServiceOfferingId = etcdNodeServiceOfferingId;
    }

    public Long getEtcdNodeCount() {
        return etcdNodeCount != null ? etcdNodeCount : 0L;
    }

    public void setEtcdNodeCount(Long etcdNodeCount) {
        this.etcdNodeCount = etcdNodeCount;
    }

    public Long getEtcdNodeTemplateId() {
        return etcdNodeTemplateId;
    }

    public void setEtcdNodeTemplateId(Long etcdNodeTemplateId) {
        this.etcdNodeTemplateId = etcdNodeTemplateId;
    }

    public Long getWorkerNodeTemplateId() {
        return workerNodeTemplateId;
    }

    public void setWorkerNodeTemplateId(Long workerNodeTemplateId) {
        this.workerNodeTemplateId = workerNodeTemplateId;
    }

    public Long getControlNodeTemplateId() {
        return controlNodeTemplateId;
    }

    public void setControlNodeTemplateId(Long controlNodeTemplateId) {
        this.controlNodeTemplateId = controlNodeTemplateId;
    }

    public Long getCniConfigId() {
        return cniConfigId;
    }

    public void setCniConfigId(Long cniConfigId) {
        this.cniConfigId = cniConfigId;
    }

    public String getCniConfigDetails() {
        return cniConfigDetails;
    }

    public void setCniConfigDetails(String cniConfigDetails) {
        this.cniConfigDetails = cniConfigDetails;
    }

}
