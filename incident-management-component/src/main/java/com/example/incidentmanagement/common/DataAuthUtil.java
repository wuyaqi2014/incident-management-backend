package com.example.incidentmanagement.common;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
public class DataAuthUtil {

    private static final List<String> SUPREME_ADMIN_LIST = List.of("system", "admin");

    public static boolean isSupreme(String userName) {
        return StringUtils.isNoneBlank(userName) && SUPREME_ADMIN_LIST.contains(userName);
    }

}
