package com.example.incidentmanagement.common;

import com.example.incidentmanagement.common.enums.PlatformErrorCode;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
public class PlatformException extends RuntimeException {

    private final int code;
    private final String message;
    private final String[] args;
    private final String errorUrl;

    public PlatformException(int code) {
        this(code, null, null, null);
    }

    public PlatformException(int code, String message) {
        this.code = code;
        this.message = message;
        this.args = null;
        this.errorUrl = null;
    }

    public PlatformException(int code, String message, String[] args, String errorUrl) {
        this.code = code;
        this.message = message;
        this.args = args;
        this.errorUrl = errorUrl;

    }

    public PlatformException(int code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
        this.message = message;
        this.args = null;
        this.errorUrl = null;
    }

    public static PlatformException ofMessage(int code, String message) {
        return new PlatformException(code, message);
    }


    public static PlatformException ofMessageAndThrowable(int code, String message, Throwable throwable) {
        return new PlatformException(code, message, throwable);
    }

    public static PlatformException ofFormat(int code, String... args) {
        return new PlatformException(code, null, args, null);
    }

    public static PlatformException ofErrorUrl(int code, String url) {
        return new PlatformException(code, null, null, url);
    }

    public static void throwIf(boolean condition, int code, String message) {
        if (condition) {
            throw new PlatformException(code, message);
        }
    }

    public static void throwIf(boolean condition, PlatformErrorCode errorCode) {
        throwIf(condition, errorCode.getCode(), errorCode.getMsg());
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String[] getArgs() {
        return args;
    }

    public String getErrorUrl() {
        return errorUrl;
    }
}
