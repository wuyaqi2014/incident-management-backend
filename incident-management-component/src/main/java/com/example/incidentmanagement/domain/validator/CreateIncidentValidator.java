package com.example.incidentmanagement.domain.validator;

import com.example.incidentmanagement.domain.repository.IncidentRepository;

import lombok.Builder;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Builder
public class CreateIncidentValidator implements Validator{

    private String operator;

    private String title;

    private IncidentRepository incidentRepository;

    @Override
    public void validate() {
        // 权限校验
        PermissionValidator.builder()
                .operator(operator)
                .build().validate();
        // title字段长度校验
        TitleValidator.builder()
                .title(title)
                .operator(operator)
                .incidentRepository(incidentRepository)
                .build().validate();
        // todo startTime、endTime校验
    }
}
