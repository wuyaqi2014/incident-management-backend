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
//@ExtendWith(MockitoExtension.class)
class PermissionValidatorTest {


    @Test
    void testValidateForSupremeAdmin() {
        PermissionValidator validator = PermissionValidator.builder()
                .operator("supremeAdmin")
                .build();
        assertDoesNotThrow(() -> validator.validate());
    }

    @Test
    void testValidateForNonSupremeAdminSameUser() {
        PermissionValidator validator = PermissionValidator.builder()
                .incidentId(1L)
                .operator("normalUser")
                .dbCreator("normalUser")
                .build();
        assertDoesNotThrow(() -> validator.validate());
    }

    @Test
    void testValidateForNonSupremeAdminDifferentUser() {
        PermissionValidator validator = PermissionValidator.builder()
                .incidentId(1L)
                .operator("differentUser")
                .dbCreator("originalUser")
                .build();

        Exception exception = assertThrows(PlatformException.class, () -> validator.validate());
        String expectedMessage = "not permission";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }
}