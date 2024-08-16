package com.example.incidentmanagement.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.incidentmanagement.common.enums.IncidentStatus;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.persisitence.entity.Incident;


/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-16
 */

class UpdateStatusValidatorTest {

    private UpdateStatusValidator.UpdateStatusValidatorBuilder validatorBuilder;
    private Incident mockIncident;

    @BeforeEach
    void setUp() {
        mockIncident = mock(Incident.class);
        validatorBuilder = UpdateStatusValidator.builder();
        validatorBuilder.incidentDB(mockIncident);
    }

    @Test
    void testValidateForPendingStatus() {
        when(mockIncident.getStatus()).thenReturn(IncidentStatus.PENDING.getValue());
        assertDoesNotThrow(() -> validatorBuilder.build().validate(), "Validation should pass for PENDING status");
    }

    @Test
    void testValidateForInProgressStatus() {
        when(mockIncident.getStatus()).thenReturn(IncidentStatus.IN_PROGRESS.getValue());
        assertDoesNotThrow(() -> validatorBuilder.build().validate(), "Validation should pass for IN_PROGRESS status");
    }

    @Test
    void testValidateForResolvedStatus() {
        when(mockIncident.getStatus()).thenReturn(IncidentStatus.RESOLVED.getValue());

        // Act & Assert
        Exception exception = assertThrows(PlatformErrorCode.SERVER_ERROR.toException("").getClass(), () -> {
            validatorBuilder.build().validate();
        }, "Validation should fail for RESOLVED status");

        assertEquals("Incident status: RESOLVED is not editable", exception.getMessage());


    }

    @Test
    void testValidateForClosedStatus() {
        when(mockIncident.getStatus()).thenReturn(IncidentStatus.DELETED.getValue());
        // Act & Assert
        Exception exception = assertThrows(PlatformErrorCode.SERVER_ERROR.toException("").getClass(), () -> {
            validatorBuilder.build().validate();
        }, "Validation should fail for DELETED status");

        assertEquals("Incident status: DELETED is not editable", exception.getMessage());

    }


}