package com.example.incidentmanagement.common.enums;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
public enum IncidentStatus {
    UNKNOWN(0, "未知"),
    PENDING(1, "待处理"),
    IN_PROGRESS(2, "处理中"),
    RESOLVED(3, "已解决"),
    DELETED(4, "已删除");


    // 状态码
    private int value;
    // 状态描述
    private String name;

    IncidentStatus(int value, String name) {
        this.name = name;
        this.value = value;
    }

    public static IncidentStatus fromValue(int value) {
        for (IncidentStatus incidentStatus : IncidentStatus.values()) {
            if (incidentStatus.value == value) {
                return incidentStatus;
            }
        }
        throw new IllegalArgumentException("Invalid IncidentStatus value: " + value);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
