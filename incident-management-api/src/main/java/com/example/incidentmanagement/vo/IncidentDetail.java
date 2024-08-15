package com.example.incidentmanagement.vo;

import com.example.incidentmanagement.persisitence.entity.Incident;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Data
public class IncidentDetail {

    @ApiModelProperty(value = "incidentId")
    private Long id;

    @ApiModelProperty(value = "title")
    private String title;

    @ApiModelProperty(value = "description")
    private String description;

    // 状态 toto 增加状态枚举
    @ApiModelProperty(value = "status")
    private int status;

    // 事件开始时间
    @ApiModelProperty(value = "startTime")
    private Long startTime;

    @ApiModelProperty(value = "endTimeTime")
    private Long endTime;

    @ApiModelProperty(value = "remark")
    private String remark;

    @ApiModelProperty(value = "createdBy")
    private String createdBy;

    @ApiModelProperty(value = "updatedBy")
    private String updatedBy;

    @ApiModelProperty(value = "createdTime")
    private Long createdTime;

    @ApiModelProperty(value = "updatedTime")
    private Long updatedTime;

    public IncidentDetail(Incident incident) {
        this.id = incident.getId();
        this.title = incident.getTitle();
        this.description = incident.getDescription();
        this.status = incident.getStatus();
        this.startTime = incident.getStartTime();
        this.endTime = incident.getEndTime();
        this.remark = incident.getRemark();
        this.createdBy = incident.getOperator();
        this.updatedBy = incident.getUpdatedBy();
        this.createdTime = incident.getCreatedTime();
        this.updatedTime = incident.getUpdatedTime();
    }
}
