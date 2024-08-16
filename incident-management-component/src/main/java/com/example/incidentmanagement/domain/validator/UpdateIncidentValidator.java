package com.example.incidentmanagement.domain.validator;

import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.persisitence.entity.Incident;

import lombok.Builder;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Builder
public class UpdateIncidentValidator implements Validator {

    private Long incidentId;

    private String title;

    private Incident incidentDB;

    private String operator;

    private IncidentRepository incidentRepository;


    @Override
    public void validate() {
        PermissionValidator.builder()
                .incidentId(incidentId)
                .operator(operator)
                .dbCreator(incidentDB.getOperator())
                .build().validate();
        TitleValidator.builder()
                .title(title)
                .operator(operator)
                .incidentRepository(incidentRepository)
                .incidentId(incidentId)
                .build().validate();
        UpdateStatusValidator.builder()
                .incidentDB(incidentDB)
                .build().validate();
    }


}
