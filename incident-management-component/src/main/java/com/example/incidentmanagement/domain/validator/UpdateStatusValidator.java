package com.example.incidentmanagement.domain.validator;

import com.example.incidentmanagement.persisitence.entity.Incident;
import com.example.incidentmanagement.common.enums.IncidentStatus;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.google.common.collect.ImmutableList;
import lombok.Builder;

import java.util.List;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Builder
public class UpdateStatusValidator implements Validator{

    private Incident incidentDB;


    public static final List<Integer> CAN_UPDATE_STATUS_LIST = new ImmutableList.Builder<Integer>()
            .add(IncidentStatus.PENDING.getValue())
            .add(IncidentStatus.IN_PROGRESS.getValue())
            .build();
    @Override
    public void validate() {
        if (!CAN_UPDATE_STATUS_LIST.contains(incidentDB.getStatus())) {
            throw PlatformErrorCode.SERVER_ERROR.toException("事件状态:{}不可编辑",
                    IncidentStatus.fromValue(incidentDB.getStatus()).getName());
        }
    }

}
