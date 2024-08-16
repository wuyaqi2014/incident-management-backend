package com.example.incidentmanagement.model;

import com.example.incidentmanagement.common.enums.PlatformErrorCode;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Data
@NoArgsConstructor
public class ResponseObject<T> {

    public static final String RESPONSE_MESSAGE_OK = "ok";

    private Integer result;
    private String errorMsg;
    private T data;

    public boolean isSuccess() {
        return this.result != null && this.result == PlatformErrorCode.OK.getCode();
    }

    public ResponseObject(Integer responseCode, String responseMsg, T responseData) {
        this.result = responseCode;
        this.errorMsg = responseMsg;
        this.data = responseData;
    }

    public static <T> ResponseObject<T> ofOk(T t) {
        return ofErrorCode(PlatformErrorCode.OK, t);
    }

    public static <T> ResponseObject<T> ofErrorCode(PlatformErrorCode errorCode, T t) {
        return new ResponseObject<>(errorCode.getCode(), errorCode.getMsg(), t);
    }

    public static ResponseObject ofErrorCodeWithMessageAndTrace(PlatformErrorCode errorCode, String message) {
        return ofErrorCodeWithCodeAndTrace(errorCode.getCode(), message);
    }

    public static ResponseObject ofErrorCodeWithCodeAndTrace(Integer responseCode, String message) {
        ResponseObject responseObject = new ResponseObject();
        responseObject.setResult(responseCode);
        responseObject.setErrorMsg(message);
        return responseObject;
    }
}
