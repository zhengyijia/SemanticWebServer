package com.siat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private final static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static void i(String info) {
        logger.info(info);
    }

    public static void d(String info) {
        logger.debug(info);
    }

    public static void e(String info) {
        logger.error(info);
    }

}
