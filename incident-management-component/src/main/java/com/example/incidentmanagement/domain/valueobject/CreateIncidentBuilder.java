package com.example.incidentmanagement.domain.valueobject;

import org.apache.commons.lang3.StringUtils;

import com.example.incidentmanagement.persisitence.entity.Incident;
import com.example.incidentmanagement.common.enums.IncidentStatus;
import lombok.Builder;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Builder
public class CreateIncidentBuilder {

    private String operator;

    private IncidentRequest incidentRequest;

    public Incident build() {
        Incident incident = new Incident();
        incident.setTitle(incidentRequest.getTitle());
        incident.setDescription(incidentRequest.getDescription());
        incident.setStartTime(incidentRequest.getStartTime());
        incident.setEndTime(incidentRequest.getEndTime());
        incident.setRemark(StringUtils.defaultString(incidentRequest.getRemark(), ""));
        incident.setOperator(operator);
        incident.setStatus(IncidentStatus.PENDING.getValue());
        incident.setUpdatedBy(operator);
        incident.setCreatedTime(System.currentTimeMillis());
        incident.setUpdatedTime(System.currentTimeMillis());
        return incident;
    }
}
