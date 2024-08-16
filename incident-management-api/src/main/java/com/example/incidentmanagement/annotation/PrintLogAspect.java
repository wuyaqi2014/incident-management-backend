package com.example.incidentmanagement.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.incidentmanagement.sso.SSOUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Slf4j
@Aspect
@Component
public class PrintLogAspect {

    @Pointcut("@annotation(com.example.incidentmanagement.annotation.PrintLog)")
    public void printLogAspect() {

    }

    @Before(value = "printLogAspect()")
    public void logDoBefore(JoinPoint joinPoint) {
    }

    @AfterReturning(value = "printLogAspect()", returning = "returnValue")
    public void logAfterReturn(JoinPoint joinPoint, Object returnValue) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            PrintLog printLog = method.getAnnotation(PrintLog.class);
            log.info("{} method:{} {} request param {}，return:{}",
                    SSOUtil.getUserName(), method.getName(), printLog.description(),
                    getArgsString(joinPoint.getArgs()),
                    returnValue.toString());
        } catch (Exception e) {
            log.error("Failed to print input parameters", e);
        }
    }

    @AfterThrowing(value = "printLogAspect()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            PrintLog printLog = method.getAnnotation(PrintLog.class);
            log.info("{} method:{} {}  args : {}",
                    SSOUtil.getUserName(), method.getName(),
                    printLog.description() + "fail ", getArgsString(joinPoint.getArgs()));
        } catch (Exception e) {
            log.error("Failed to print input parameters", e);
        }

    }

    /**
     * print input params，filterrequest response
     * @param args
     * @return
     */
    private String getArgsString(Object[] args) {
        try {
            if (args != null && args.length > 0) {
                List<Object> result = new ArrayList<>();
                for (Object arg : args) {
                    if (arg instanceof HttpServletRequest) {
                        continue;
                    }
                    if (arg instanceof HttpServletResponse) {
                        continue;
                    }
                    //附件不输入入参日志
                    if (arg instanceof MultipartFile) {
                        continue;
                    }
                    result.add(arg);
                }
                return result.toString();
            }
        } catch (Exception e) {
            log.error("Failed to print input parameters:{}", e);
        }
        return "";
    }

}
