package com.javenluo.ranger.scheduler.utils;

import javax.sql.DataSource;

/**
 * 调度器数据源
 * @author gulong
 * @version 2017-10-16
 */
public class SchedulerDataSource {
	public String toString() {
		return "SchedulerDataSource(useDB=" + getUseDB() + ", dataSource=" + getDataSource() + ")";
	}

	public int hashCode() {
		int PRIME = 59;
		int result = 1;
		Object $useDB = getUseDB();
		result = result * 59 + ($useDB == null ? 43 : $useDB.hashCode());
		Object $dataSource = getDataSource();
		result = result * 59 + ($dataSource == null ? 43 : $dataSource.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof SchedulerDataSource;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SchedulerDataSource)) {
			return false;
		}
		SchedulerDataSource other = (SchedulerDataSource) o;
		if (!other.canEqual(this)) {
			return false;
		}
		Object this$useDB = getUseDB();
		Object other$useDB = other.getUseDB();
		if (this$useDB == null ? other$useDB != null : !this$useDB.equals(other$useDB)) {
			return false;
		}
		Object this$dataSource = getDataSource();
		Object other$dataSource = other.getDataSource();
		return this$dataSource == null ? other$dataSource == null : this$dataSource.equals(other$dataSource);
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUseDB(String useDB) {
		this.useDB = useDB;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public String getUseDB() {
		return this.useDB;
	}

	private String useDB = "true";
	private DataSource dataSource;

	public DataSource getSchedulerDataSource() {
		if ((this.useDB == null) || ("true".equalsIgnoreCase(this.useDB.trim()))) {
			return getDataSource();
		}
		return null;
	}
}
