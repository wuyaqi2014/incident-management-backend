package com.example.incidentmanagement.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.example.incidentmanagement.domain.valueobject.IncidentRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
public class IncidentParam {

    @ApiModelProperty(value = "title")
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @ApiModelProperty(value = "description")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @ApiModelProperty(value = "startTime, in milliseconds timestamp")
    @NotNull(message = "StartTime cannot be empty")
    @Positive
    private Long startTime;

    @ApiModelProperty(value = "endTime，in milliseconds timestamp")
    @NotNull(message = "EndTime cannot be empty")
    @Positive
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
