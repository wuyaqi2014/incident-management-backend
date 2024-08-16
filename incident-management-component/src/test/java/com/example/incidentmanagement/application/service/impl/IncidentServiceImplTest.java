package com.example.incidentmanagement.application.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.domain.validator.CreateIncidentValidator;
import com.example.incidentmanagement.domain.validator.UpdateIncidentValidator;
import com.example.incidentmanagement.domain.valueobject.IncidentRequest;
import com.example.incidentmanagement.domain.valueobject.IncidentResult;
import com.example.incidentmanagement.domain.valueobject.PageInfo;
import com.example.incidentmanagement.persisitence.entity.Incident;

public class IncidentServiceImplTest {

    @InjectMocks
    private IncidentServiceImpl incidentService;

    @Mock
    private IncidentRepository incidentRepository;

    @Mock
    private CreateIncidentValidator createIncidentValidator;

    @Mock
    private UpdateIncidentValidator updateIncidentValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateIncident_ValidRequest_ShouldPass() {
        // Arrange
        IncidentRequest incidentRequest = mock(IncidentRequest.class);
        when(incidentRequest.getTitle()).thenReturn("Valid Title");
        doNothing().when(incidentRepository).createIncident(any(Incident.class));

        // todo donoting，但是要修改incident的值
        // Act
        IncidentResult result = incidentService.createIncident(incidentRequest, "operator1");

        // Assert
        verify(createIncidentValidator).validate(); // Ensure validation was called
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testCreateIncident_InvalidRequest_ShouldThrowException() {
        // Arrange
        IncidentRequest incidentRequest = mock(IncidentRequest.class);
        when(incidentRequest.getTitle()).thenReturn("");

        doThrow(PlatformErrorCode.PARAM_ERROR.toException("Title cannot be empty"))
                .when(createIncidentValidator).validate();

        // Act & Assert
        Exception exception = assertThrows(PlatformErrorCode.PARAM_ERROR.toException("").getClass(), () -> {
            incidentService.createIncident(incidentRequest, "operator1");
        });
        assertEquals("Title cannot be empty", exception.getMessage());
    }

    @Test
    public void testUpdateIncident_ValidRequest_ShouldPass() {
        // Arrange
        Long incidentID = 1L;
        IncidentRequest updateIncident = mock(IncidentRequest.class);
        when(updateIncident.getTitle()).thenReturn("Updated Title");
        Incident incidentDB = mock(Incident.class);

        when(incidentRepository.getIncident(incidentID, "operator1")).thenReturn(incidentDB);

        // todo
        // Act
        IncidentResult result = incidentService.updateIncident(incidentID, updateIncident, "operator1");

        // Assert
        verify(updateIncidentValidator).validate();
        assertNotNull(result);
        assertTrue(result.isSuccess());
    }

    @Test
    public void testUpdateIncident_IncidentNotFound_ShouldThrowException() {
        // Arrange
        Long incidentID = 1L;
        IncidentRequest updateIncident = mock(IncidentRequest.class);

        when(incidentRepository.getIncident(incidentID, "operator1")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(PlatformErrorCode.SERVER_ERROR.toException("").getClass(), () -> {
            incidentService.updateIncident(incidentID, updateIncident, "operator1");
        });
        assertEquals("incidentID:1 not exists", exception.getMessage());
    }

    @Test
    public void testDeleteIncident_ValidIncident_ShouldPass() {
        // Arrange
        Long incidentID = 1L;
        Incident incidentDB = mock(Incident.class);

        when(incidentRepository.getIncident(incidentID, "operator1")).thenReturn(incidentDB);
        when(incidentRepository.deleteIncident(incidentID)).thenReturn(true);

        // Act
        boolean result = incidentService.deleteIncident(incidentID, "operator1");

        // Assert
        assertTrue(result);
    }

    @Test
    public void testDeleteIncident_IncidentNotFound_ShouldThrowException() {
        // Arrange
        Long incidentID = 1L;

        when(incidentRepository.getIncident(incidentID, "operator1")).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(PlatformErrorCode.SERVER_ERROR.toException("").getClass(), () -> {
            incidentService.deleteIncident(incidentID, "operator1");
        });
        assertEquals("incidentID:1 not exists", exception.getMessage());
    }

    @Test
    public void testGetIncidentById_IncidentExists_ShouldReturnIncident() {
        // Arrange
        Long incidentID = 1L;
        Incident incident = mock(Incident.class);

        when(incidentRepository.getIncidentById(incidentID)).thenReturn(incident);

        // Act
        Incident result = incidentService.getIncidentById(incidentID);

        // Assert
        assertNotNull(result);
        assertEquals(incident, result);
    }

    @Test
    public void testGetIncidentById_IncidentNotFound_ShouldReturnNull() {
        // Arrange
        Long incidentID = 1L;

        when(incidentRepository.getIncidentById(incidentID)).thenReturn(null);

        // Act
        Incident result = incidentService.getIncidentById(incidentID);

        // Assert
        assertNull(result);
    }

    @Test
    public void testListAll_ValidPageInfo_ShouldReturnPageOfIncidents() {
        // Arrange
        PageInfo pageInfo = mock(PageInfo.class);
        Page<Incident> page = mock(Page.class);

        when(incidentRepository.listAll(pageInfo, "operator1")).thenReturn(page);

        // Act
        Page<Incident> result = incidentService.listAll(pageInfo, "operator1");

        // Assert
        assertNotNull(result);
        assertEquals(page, result);
    }
}
