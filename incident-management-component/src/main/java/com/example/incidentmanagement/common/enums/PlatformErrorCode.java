package com.example.incidentmanagement.common.enums;

import com.example.incidentmanagement.common.PlatformException;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
public enum PlatformErrorCode {

    OK(1, "成功"),


    // 2~99 前端入参相关异常
    PARAM_ERROR(2, "参数异常"),


    // 100~199 后端相关异常
    SERVER_ERROR(100, "服务端异常"),


    // 200+ 其它异常
    ENUM_INVALID(200, "无效枚举值"),

    NO_PERMISSION(201, "无操作权限");

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

    public PlatformException toException() {
        return PlatformException.ofMessage(code, msg);
    }

    public PlatformException toException(String overrideMsg) {
        return PlatformException.ofMessage(code, overrideMsg);
    }

    public PlatformException toException(String overrideMsg, Object... param) {
        String formatMsg = MessageFormatter.arrayFormat(overrideMsg, param).getMessage();
        return PlatformException.ofMessage(code, formatMsg);
    }

    public PlatformException toException(String overrideMsg, Throwable throwable, Object... param) {
        String formatMsg = MessageFormatter.arrayFormat(overrideMsg, param).getMessage();
        return PlatformException.ofMessageAndThrowable(code, formatMsg, throwable);
    }
}
