package com.example.incidentmanagement.domain.valueobject;

import org.apache.commons.lang3.StringUtils;

import com.example.incidentmanagement.persisitence.entity.Incident;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import lombok.Builder;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Builder
public class UpdateIncidentBuilder {

    private String operator;

    private IncidentRequest incidentRequest;

    private IncidentRepository incidentRepository;

    private Long incidentId;

    public Incident build() {
        Incident incidentDB = incidentRepository.getIncident(incidentId, operator);
        if (incidentDB == null) {
            throw PlatformErrorCode.PARAM_ERROR.toException("incidentId:{} not exist", incidentId);
        }
        incidentDB.setTitle(incidentRequest.getTitle());
        incidentDB.setDescription(incidentRequest.getDescription());
        incidentDB.setStartTime(incidentRequest.getStartTime());
        incidentDB.setEndTime(incidentRequest.getEndTime());
        incidentDB.setRemark(StringUtils.defaultString(incidentRequest.getRemark(), ""));
        incidentDB.setUpdatedBy(operator);
        incidentDB.setStatus(1); // todo：根据事件判断当前状态
        incidentDB.setUpdatedTime(System.currentTimeMillis());
        return incidentDB;
    }

}
