package com.example.incidentmanagement.domain.valueobject;

import lombok.Builder;
import lombok.Data;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
@Builder
public class IncidentResult {

    private long incidentId; // 事件id

    private boolean success; // 操作结果

    private String tipMsg; // 提示信息


    public static IncidentResult success(long incidentId) {
        return IncidentResult.builder()
                .incidentId(incidentId)
                .success(true)
                .build();
    }

}
