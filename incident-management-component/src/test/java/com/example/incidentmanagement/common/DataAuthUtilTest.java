package com.example.incidentmanagement.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-15
 */
class DataAuthUtilTest {

    @Test
    void testIsSupremeWithSupremeAdmin() {
        // Test with supreme admin usernames from the list
        assertTrue(DataAuthUtil.isSupreme("system"), "The system admin 'system' should be recognized as a supreme admin");
        assertTrue(DataAuthUtil.isSupreme("admin"), "The admin 'admin' should be recognized as a supreme admin");
    }

    @Test
    void testIsSupremeWithNonSupremeAdmin() {
        // Test with non-supreme admin usernames
        assertFalse(DataAuthUtil.isSupreme("user1"), "A regular user 'user1' should not be recognized as a supreme admin");
        assertFalse(DataAuthUtil.isSupreme("manager"), "A manager 'manager' should not be recognized as a supreme admin");
    }

    @Test
    void testIsSupremeWithCaseInsensitiveMatch() {
        // Test case-insensitive matching
        assertTrue(DataAuthUtil.isSupreme("SYSTEM"), "'SYSTEM' should match 'system' and return true");
        assertTrue(DataAuthUtil.isSupreme("ADMIN"), "'ADMIN' should match 'admin' and return true");
    }

    @Test
    void testIsSupremeWithEmptyString() {
        // Test with an empty string
        assertFalse(DataAuthUtil.isSupreme(""), "An empty string should return false");
    }

    @Test
    void testIsSupremeWithNull() {
        // Test with null value
        assertFalse(DataAuthUtil.isSupreme(null), "A null value should return false");
    }

    @Test
    void testIsSupremeWithBlankString() {
        // Test with a blank string (only spaces)
        assertFalse(DataAuthUtil.isSupreme(" "), "A string containing only spaces should return false");
    }
}