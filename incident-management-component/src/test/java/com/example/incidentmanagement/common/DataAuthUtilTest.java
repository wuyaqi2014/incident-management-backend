package com.example.incidentmanagement.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-15
 */
class DataAuthUtilTest {

    @Test
    void testIsSupremeForSupremeAdmin() {
        // 测试超级管理员用户名
        assertTrue(DataAuthUtil.isSupreme("system"), "系统超级管理员应该返回true");
        assertTrue(DataAuthUtil.isSupreme("admin"), "普通超级管理员应该返回true");
    }

    @Test
    void testIsSupremeForNonSupremeAdmin() {
        // 测试非超级管理员用户名
        assertFalse(DataAuthUtil.isSupreme("regularUser"), "常规用户应该返回false");
        assertFalse(DataAuthUtil.isSupreme("guest"), "访客用户应该返回false");
    }

    @Test
    void testIsSupremeForEmptyUserName() {
        // 测试空或空白用户名
        assertFalse(DataAuthUtil.isSupreme(""), "空字符串用户名应该返回false");
        assertFalse(DataAuthUtil.isSupreme(" "), "空白字符串用户名应该返回false");
        assertFalse(DataAuthUtil.isSupreme(null), "null用户名应该返回false");
    }

    @Test
    void testIsSupremeForCaseSensitivity() {
        // 测试大小写敏感性
        assertFalse(DataAuthUtil.isSupreme("System"), "大小写敏感，System应该返回false");
        assertFalse(DataAuthUtil.isSupreme("ADMIN"), "大小写敏感，ADMIN应该返回false");
    }
}