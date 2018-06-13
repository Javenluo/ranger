package com.javenluo.ranger.common.dynamicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源切换
 * @author guyu
 * @version 2017-11-20
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	
	private static final Logger logger = LoggerFactory.getLogger(RoutingDataSource.class);

	/**
	 * 获取当前路由数据源
	 **/
	@Override
	protected Object determineCurrentLookupKey() {
		//logger.info("当前路由数据源：" + DbContextHolder.getDbType());
		return DbContextHolder.getDbType();
	}

}
