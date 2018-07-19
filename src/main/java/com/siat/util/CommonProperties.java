package com.siat.util;

import java.util.Properties;

public class CommonProperties {

    // virtuoso数据源配置信息
    private static final Properties VIRTUOSO_SOURCE = ServletUtils
            .getProperties("/properties/virtuoso_datasource.properties");
    public static final String GRAPH_NAME = VIRTUOSO_SOURCE.getProperty("GRAPH_NAME");
    public static final String URL_HOSTLIST = VIRTUOSO_SOURCE.getProperty("URL_HOSTLIST");
    public static final String USER = VIRTUOSO_SOURCE.getProperty("USER");
    public static final String PASSWORD = VIRTUOSO_SOURCE.getProperty("PASSWORD");

}
