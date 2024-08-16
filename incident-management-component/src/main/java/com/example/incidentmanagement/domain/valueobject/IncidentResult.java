package com.example.incidentmanagement.domain.valueobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncidentResult {

    private long incidentId;

    private boolean success;

    private String tipMsg;


    public static IncidentResult success(long incidentId) {
        return IncidentResult.builder()
                .incidentId(incidentId)
                .success(true)
                .build();
    }

}
