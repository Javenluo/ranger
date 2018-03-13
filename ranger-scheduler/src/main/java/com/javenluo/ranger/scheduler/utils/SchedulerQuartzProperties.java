package com.javenluo.ranger.scheduler.utils;

import java.util.Properties;

/**
 * 任务调度属性
 * @author gulong
 * @version 2017-10-16
 */
public class SchedulerQuartzProperties {
	public String toString() {
		return "SchedulerQuartzProperties(threadCount=" + getThreadCount() + ", driverDelegateClass="
				+ getDriverDelegateClass() + ", useDB=" + getUseDB() + ", instanceId=" + getInstanceId()
				+ ", clusterCheckinInterval=" + getClusterCheckinInterval() + ", clusterFlag=" + isClusterFlag()
				+ ", jmxExport=" + isJmxExport() + ", selectWithLockSQL=" + getSelectWithLockSQL() + ")";
	}

	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $threadCount = getThreadCount();
		result = result * 59 + ($threadCount == null ? 43 : $threadCount.hashCode());
		Object $driverDelegateClass = getDriverDelegateClass();
		result = result * 59 + ($driverDelegateClass == null ? 43 : $driverDelegateClass.hashCode());
		Object $useDB = getUseDB();
		result = result * 59 + ($useDB == null ? 43 : $useDB.hashCode());
		Object $instanceId = getInstanceId();
		result = result * 59 + ($instanceId == null ? 43 : $instanceId.hashCode());
		Object $clusterCheckinInterval = getClusterCheckinInterval();
		result = result * 59 + ($clusterCheckinInterval == null ? 43 : $clusterCheckinInterval.hashCode());
		result = result * 59 + (isClusterFlag() ? 79 : 97);
		result = result * 59 + (isJmxExport() ? 79 : 97);
		Object $selectWithLockSQL = getSelectWithLockSQL();
		result = result * 59 + ($selectWithLockSQL == null ? 43 : $selectWithLockSQL.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof SchedulerQuartzProperties;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SchedulerQuartzProperties)) {
			return false;
		}
		SchedulerQuartzProperties other = (SchedulerQuartzProperties) o;
		if (!other.canEqual(this)) {
			return false;
		}
		Object this$threadCount = getThreadCount();
		Object other$threadCount = other.getThreadCount();
		if (this$threadCount == null ? other$threadCount != null : !this$threadCount.equals(other$threadCount)) {
			return false;
		}
		Object this$driverDelegateClass = getDriverDelegateClass();
		Object other$driverDelegateClass = other.getDriverDelegateClass();
		if (this$driverDelegateClass == null ? other$driverDelegateClass != null
				: !this$driverDelegateClass.equals(other$driverDelegateClass)) {
			return false;
		}
		Object this$useDB = getUseDB();
		Object other$useDB = other.getUseDB();
		if (this$useDB == null ? other$useDB != null : !this$useDB.equals(other$useDB)) {
			return false;
		}
		Object this$instanceId = getInstanceId();
		Object other$instanceId = other.getInstanceId();
		if (this$instanceId == null ? other$instanceId != null : !this$instanceId.equals(other$instanceId)) {
			return false;
		}
		Object this$clusterCheckinInterval = getClusterCheckinInterval();
		Object other$clusterCheckinInterval = other.getClusterCheckinInterval();
		if (this$clusterCheckinInterval == null ? other$clusterCheckinInterval != null
				: !this$clusterCheckinInterval.equals(other$clusterCheckinInterval)) {
			return false;
		}
		if (isClusterFlag() != other.isClusterFlag()) {
			return false;
		}
		if (isJmxExport() != other.isJmxExport()) {
			return false;
		}
		Object this$selectWithLockSQL = getSelectWithLockSQL();
		Object other$selectWithLockSQL = other.getSelectWithLockSQL();
		return this$selectWithLockSQL == null ? other$selectWithLockSQL == null
				: this$selectWithLockSQL.equals(other$selectWithLockSQL);
	}

	public void setSelectWithLockSQL(String selectWithLockSQL) {
		this.selectWithLockSQL = selectWithLockSQL;
	}

	public void setJmxExport(boolean jmxExport) {
		this.jmxExport = jmxExport;
	}

	public void setClusterFlag(boolean clusterFlag) {
		this.clusterFlag = clusterFlag;
	}

	public void setClusterCheckinInterval(String clusterCheckinInterval) {
		this.clusterCheckinInterval = clusterCheckinInterval;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public void setUseDB(String useDB) {
		this.useDB = useDB;
	}

	public void setDriverDelegateClass(String driverDelegateClass) {
		this.driverDelegateClass = driverDelegateClass;
	}

	public void setThreadCount(String threadCount) {
		this.threadCount = threadCount;
	}

	public String getSelectWithLockSQL() {
		return this.selectWithLockSQL;
	}

	public boolean isJmxExport() {
		return this.jmxExport;
	}

	public boolean isClusterFlag() {
		return this.clusterFlag;
	}

	public String getClusterCheckinInterval() {
		return this.clusterCheckinInterval;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public String getUseDB() {
		return this.useDB;
	}

	public String getDriverDelegateClass() {
		return this.driverDelegateClass;
	}

	public String getThreadCount() {
		return this.threadCount;
	}

	private String threadCount = "10";
	private String driverDelegateClass = "org.quartz.impl.jdbcjobstore.StdJDBCDelegate";
	private String useDB = "true";
	private String instanceId;
	private String clusterCheckinInterval;
	private boolean clusterFlag = false;
	private boolean jmxExport = false;
	private String selectWithLockSQL;

	public Properties getQuartzPropeties() {
		Properties quartzProperties = new Properties();
		quartzProperties.put("org.quartz.threadPool.threadCount", getThreadCount());
		boolean isUseDb = (this.useDB == null) || ("true".equalsIgnoreCase(getUseDB()));
		if (isUseDb) {
			quartzProperties.put("org.quartz.jobStore.driverDelegateClass", getDriverDelegateClass());
		}
		if ((this.instanceId != null) && (!"".equals(this.instanceId.trim()))) {
			quartzProperties.put("org.quartz.scheduler.instanceId", getInstanceId());
		}
		if ((isUseDb) && (this.selectWithLockSQL != null) && (!"".equals(this.selectWithLockSQL.trim()))) {
			quartzProperties.put("org.quartz.jobStore.selectWithLockSQL", getSelectWithLockSQL());
		}
		if (isClusterFlag()) {
			quartzProperties.put("org.quartz.jobStore.isClustered", "true");
		}
		if (isJmxExport()) {
			quartzProperties.put("org.quartz.scheduler.jmx.export", "true");
		}
		quartzProperties.put("org.quartz.scheduler.skipUpdateCheck", "true");
		return quartzProperties;
	}
}
