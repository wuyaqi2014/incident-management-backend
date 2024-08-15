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
        // 权限校验
        PermissionValidator.builder()
                .incidentId(incidentId)
                .operator(operator)
                .dbCreator(incidentDB.getOperator())
                .build().validate();
        // title字段长度校验
        TitleValidator.builder()
                .title(title)
                .operator(operator)
                .incidentRepository(incidentRepository)
                .incidentId(incidentId)
                .build().validate();
        // todo startTime、endTime校验
        // 状态校验
        UpdateStatusValidator.builder()
                .incidentDB(incidentDB)
                .build().validate();
    }


}
