package com.example.incidentmanagement.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidentRequest {

    private String title;

    private String description;

    private Long startTime;

    private Long endTime;

    private String remark;

}
