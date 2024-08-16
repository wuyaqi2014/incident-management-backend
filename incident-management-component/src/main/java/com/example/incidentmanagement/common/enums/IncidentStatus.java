package com.example.incidentmanagement.common.enums;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
public enum IncidentStatus {
    UNKNOWN(0, "UNKNOWN"),
    PENDING(1, "PENDING"),
    IN_PROGRESS(2, "IN_PROGRESS"),
    RESOLVED(3, "RESOLVED"),
    DELETED(4, "DELETED");


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
