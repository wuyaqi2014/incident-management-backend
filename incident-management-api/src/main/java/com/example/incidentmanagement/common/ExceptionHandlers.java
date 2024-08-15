package com.example.incidentmanagement.common;

import java.nio.charset.Charset;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.model.ResponseObject;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {

    @ExceptionHandler(PlatformException.class)
    @ResponseBody
    public ResponseObject handleThrowable(PlatformException e, ServletRequest servletRequest, HttpServletRequest request) {
        log.error("BrandException: {}", e.getMessage(), e);
        logServletRequestInfo(servletRequest);
        String url = request.getRequestURL()
                .append(request.getQueryString() == null ? "" : "?" + request.getQueryString())
                .toString();
        // todo 给Prometheus上报异常打点
        // PerfUtils.error(API_EXCEPTION_MONITOR_SUBTAG, url, e.getCode(), e.getMessage(), 1);
        return ResponseObject.ofErrorCodeWithCodeAndTrace(e.getCode(), e.getMessage());
    }

    //其他未处理的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObject exceptionHandler(Exception e, ServletRequest servletRequest, HttpServletRequest request) {
        log.error("Exception:", e);
        logServletRequestInfo(servletRequest);
        String url = request.getRequestURL()
                .append(request.getQueryString() == null ? "" : "?" + request.getQueryString())
                .toString();
        // todo 上报异常打点
        // PerfUtils.error(API_EXCEPTION_MONITOR_SUBTAG, url, PlatformErrorCode.SERVER_ERROR.getCode(), e.getMessage(), 1);
        return ResponseObject
                .ofErrorCodeWithMessageAndTrace(PlatformErrorCode.SERVER_ERROR, e.getMessage());
    }

    private void logServletRequestInfo(ServletRequest servletRequest) {
        if (servletRequest instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) servletRequest;
            log.error("request body: {}", StringUtils.toEncodedString(wrapper.getContentAsByteArray(),
                    Charset.forName(wrapper.getCharacterEncoding())));
        }
        if (servletRequest instanceof StandardMultipartHttpServletRequest) {
            StandardMultipartHttpServletRequest request = (StandardMultipartHttpServletRequest) servletRequest;
            log.error("multipart params: {}", ObjectMapperUtils.toJSON(request.getParameterMap()));
        }
    }

}
