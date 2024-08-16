package com.example.incidentmanagement.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.example.incidentmanagement.common.PlatformException;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
class PermissionValidatorTest {

    @Test
    void testValidateForSupremeAdmin() {
        PermissionValidator validator = PermissionValidator.builder()
                .operator("system")
                .build();
        assertDoesNotThrow(validator::validate, "Validation should not throw an exception for a supreme administrator");
    }

    @Test
    void testValidateForNonSupremeAdminSameCreator() {
        PermissionValidator validator = PermissionValidator.builder()
                .incidentId(1L)
                .operator("testUser")
                .dbCreator("testUser")
                .build();
        assertDoesNotThrow(validator::validate, "Validation should not throw an exception when the operator and creator are the same");
    }


    @Test
    void testValidateForNonSupremeAdminDifferentCreator() {
        PermissionValidator validator = PermissionValidator.builder()
                .incidentId(1L)
                .operator("testUser")
                .dbCreator("otherUser")
                .build();
        Exception exception = assertThrows(PlatformException.class, validator::validate);
        String expectedMessage = "does not have permission";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testValidateForNonSupremeAdminWithZeroIncidentId() {
        PermissionValidator validator = PermissionValidator.builder()
                .incidentId(0L)
                .operator("testUser")
                .dbCreator("otherUser")
                .build();
        assertDoesNotThrow(validator::validate, "Validation should not throw an exception when the incident ID is zero");
    }

}


