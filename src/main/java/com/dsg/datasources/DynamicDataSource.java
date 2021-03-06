package com.dsg.datasources;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态切换数据源
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceContextHolder.getDbType();
	}

}