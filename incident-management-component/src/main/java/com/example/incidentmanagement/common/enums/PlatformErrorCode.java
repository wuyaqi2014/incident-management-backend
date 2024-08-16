package com.example.incidentmanagement.common.enums;

import org.slf4j.helpers.MessageFormatter;

import com.example.incidentmanagement.common.PlatformException;
/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
public enum PlatformErrorCode {

    OK(1, "success"),


    // 2~99 front param error
    PARAM_ERROR(2, "param_error"),


    // 100~199 backend error
    SERVER_ERROR(100, "server_error"),


    // 200+ other error
    ENUM_INVALID(200, "enum_valid"),

    NO_PERMISSION(201, "no_permission");

    private int code;
    private String msg;

    PlatformErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public PlatformException toException(String overrideMsg) {
        return PlatformException.ofMessage(code, overrideMsg);
    }

    public PlatformException toException(String overrideMsg, Object... param) {
        String formatMsg = MessageFormatter.arrayFormat(overrideMsg, param).getMessage();
        return PlatformException.ofMessage(code, formatMsg);
    }
}
