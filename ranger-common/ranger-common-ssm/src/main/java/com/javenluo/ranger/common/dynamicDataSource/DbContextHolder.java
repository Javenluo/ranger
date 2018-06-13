package com.javenluo.ranger.common.dynamicDataSource;

import com.javenluo.ranger.common.dynamicDataSource.enums.DbType;

/**
 * 当前线程上下文数据源设置类   
 * @author guyu
 * @version 2017-11-20
 */
public class DbContextHolder {
	private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<DbType>();

	/**
	 * 设置数据源
	 * @param dbType 数据源
	 */
	public static void setDbType(DbType dbType) {
		if (dbType == null) {
			throw new NullPointerException("不允许设置一个空数据源");
		}
		contextHolder.set(dbType);
	}

	/**
	 * 获取数据源
	 * @return DbType 数据源
	 */
	public static DbType getDbType() {
 		return (DbType) contextHolder.get();
	}

	/**
	 * 清空数据源
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}

}
