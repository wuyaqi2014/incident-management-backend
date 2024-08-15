package com.example.incidentmanagement.common;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
public class PlatformException extends RuntimeException {

    private final int code;
    private final String message;
    private final String[] args;
    private final String errorUrl;

    public PlatformException(int code, String message) {
        this.code = code;
        this.message = message;
        this.args = null;
        this.errorUrl = null;
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
