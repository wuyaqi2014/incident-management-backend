package com.example.incidentmanagement.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.incidentmanagement.application.service.IncidentService;
import com.example.incidentmanagement.common.Page;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.domain.validator.CreateIncidentValidator;
import com.example.incidentmanagement.domain.validator.UpdateIncidentValidator;
import com.example.incidentmanagement.domain.valueobject.CreateIncidentBuilder;
import com.example.incidentmanagement.domain.valueobject.IncidentRequest;
import com.example.incidentmanagement.domain.valueobject.IncidentResult;
import com.example.incidentmanagement.domain.valueobject.PageInfo;
import com.example.incidentmanagement.domain.valueobject.UpdateIncidentBuilder;
import com.example.incidentmanagement.persisitence.entity.Incident;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Service
public class IncidentServiceImpl implements IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Override
    public IncidentResult createIncident(IncidentRequest incidentRequest, String operator) {
        // 参数校验
        CreateIncidentValidator.builder()
                .operator(operator)
                .title(incidentRequest.getTitle())
                .incidentRepository(incidentRepository)
                .build().validate();
        Incident incident = CreateIncidentBuilder.builder()
                .operator(operator)
                .incidentRequest(incidentRequest)
                .build().build();
        incidentRepository.createIncident(incident);
        return IncidentResult.success(incident.getId());
    }

    @Override
    public IncidentResult updateIncident(Long incidentID, IncidentRequest updateIncident, String operator) {
        Incident incidentDB = incidentRepository.getIncident(incidentID, operator);
        if (incidentDB == null) {
            throw PlatformErrorCode.SERVER_ERROR.toException("incidentID:{} not exists", incidentID);
        }
        UpdateIncidentValidator.builder()
                .operator(operator)
                .incidentDB(incidentDB)
                .incidentId(incidentID)
                .title(updateIncident.getTitle())
                .incidentRepository(incidentRepository)
                .build().validate();
        Incident incident = UpdateIncidentBuilder.builder()
                .incidentId(incidentID)
                .incidentRepository(incidentRepository)
                .incidentRequest(updateIncident)
                .operator(operator)
                .build().build();
        incidentRepository.updateIncident(incident);
        return IncidentResult.success(incidentID);
    }

    @Override
    public boolean deleteIncident(Long incidentID, String operator) {
        Incident incidentDB = incidentRepository.getIncident(incidentID, operator);
        if (incidentDB == null) {
            throw PlatformErrorCode.SERVER_ERROR.toException("incidentID:{} not exists", incidentID);
        }
        return incidentRepository.deleteIncident(incidentID);
    }

    @Override
    public Incident getIncidentById(Long id) {
        return incidentRepository.getIncidentById(id);
    }

    @Override
    public Page<Incident> listAll(PageInfo pageInfo, String operator) {
        return incidentRepository.listAll(pageInfo, operator);
    }
}
