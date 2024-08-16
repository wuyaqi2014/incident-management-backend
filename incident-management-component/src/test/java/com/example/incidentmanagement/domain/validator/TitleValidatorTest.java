package com.example.incidentmanagement.domain.validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.incidentmanagement.common.PlatformException;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.persisitence.entity.Incident;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-16
 */

class TitleValidatorTest {

    private TitleValidator.TitleValidatorBuilder validatorBuilder;
    private IncidentRepository mockIncidentRepository;

    @BeforeEach
    void setUp() {
        mockIncidentRepository = mock(IncidentRepository.class);
        validatorBuilder = TitleValidator.builder();
        validatorBuilder.incidentRepository(mockIncidentRepository);
    }

    @Test
    void testValidateForEmptyTitle() {
        validatorBuilder.title("");
        validatorBuilder.operator("testUser");
        Exception exception = assertThrows(PlatformException.class, () -> validatorBuilder.build().validate(),
                "Validation should fail with empty title");
        String expectedMessage = "Title cannot be empty";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testValidateForTitleTooLong() {
        validatorBuilder.title(StringUtils.repeat("a", 101));
        validatorBuilder.operator("testUser");
        Exception exception = assertThrows(PlatformException.class, () -> validatorBuilder.build().validate(),
                "Validation should fail when title exceeds 100 characters");
        String expectedMessage = "Title cannot exceed 100 characters";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);

    }

    @Test
    void testValidateForUniqueTitleOnNewIncident() {
        Incident incidentDB = new Incident();
        incidentDB.setTitle("TestTitle");
        when(mockIncidentRepository.queryIncidentByOperator("testUser", "TestTitle"))
                .thenReturn(incidentDB);

        validatorBuilder.title("TestTitle");
        validatorBuilder.operator("testUser");
        validatorBuilder.incidentId(null); // New incident

        Exception exception = assertThrows(PlatformException.class, () -> validatorBuilder.build().validate(),
                "Validation should fail with duplicate title on new incident");
        String expectedMessage = "Title must be unique for the same user";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testValidateForUniqueTitleOnEditIncident() {
        Incident incidentDB = new Incident();
        incidentDB.setId(2L);
        incidentDB.setTitle("TestTitle");
        when(mockIncidentRepository.queryIncidentByOperator("testUser", "TestTitle"))
                .thenReturn(incidentDB);

        validatorBuilder.title("TestTitle");
        validatorBuilder.operator("testUser");
        validatorBuilder.incidentId(1L); // Editing incident

        Exception exception = assertThrows(PlatformException.class, () -> validatorBuilder.build().validate(),
                "Validation should fail with duplicate title on editing incident");
        String expectedMessage = "Title must be unique for the same user";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    void testValidateForValidTitleOnNewIncident() {
        when(mockIncidentRepository.queryIncidentByOperator("testUser", "UniqueTitle"))
                .thenReturn(null);

        validatorBuilder.title("UniqueTitle");
        validatorBuilder.operator("testUser");
        validatorBuilder.incidentId(null); // New incident

        assertDoesNotThrow(() -> validatorBuilder.build().validate(), "Validation should pass with a valid unique title on new incident");
    }

    @Test
    void testValidateForValidTitleOnEditIncident() {
        Incident incidentDB = new Incident();
        incidentDB.setId(1L);
        incidentDB.setTitle("TestTitle");
        when(mockIncidentRepository.queryIncidentByOperator("testUser", "TestTitle"))
                .thenReturn(incidentDB);

        validatorBuilder.title("TestTitle");
        validatorBuilder.operator("testUser");
        validatorBuilder.incidentId(1L); // Editing incident

        assertDoesNotThrow(() -> validatorBuilder.build().validate(), "Validation should pass with a valid unique title on editing incident");
    }
}
