package com.example.incidentmanagement.param;

import com.example.incidentmanagement.domain.valueobject.IncidentRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
public class IncidentParam {

    @ApiModelProperty(value = "title")
    @NotNull
    private String title;

    @ApiModelProperty(value = "description")
    @NotNull
    private String description;

    @ApiModelProperty(value = "startTime,毫秒事件戳")
    @NotNull
    @Positive
    private Long startTime;

    @ApiModelProperty(value = "endTime，毫秒事件戳")
    private Long endTime;

    @ApiModelProperty(value = "remark")
    private String remark;

    public IncidentRequest convertToIncidentRequest() {
        return IncidentRequest.builder()
                .title(title)
                .description(description)
                .startTime(startTime)
                .endTime(endTime)
                .remark(remark)
                .build();
    }
}
