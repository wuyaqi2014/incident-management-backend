package com.example.incidentmanagement.vo;

import com.example.incidentmanagement.domain.valueobject.IncidentResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Data
public class IncidentResultVO {

    @ApiModelProperty(value = "Incident ID")
    private Long incidentId;

    @ApiModelProperty(value = "result,true:success,false:fail")
    private boolean success;

    public IncidentResultVO(IncidentResult incidentResult) {
        this.incidentId = incidentResult.getIncidentId();
        this.success = incidentResult.isSuccess();
    }
}
