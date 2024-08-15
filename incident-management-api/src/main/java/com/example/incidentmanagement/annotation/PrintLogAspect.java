package com.example.incidentmanagement.annotation;

import com.example.incidentmanagement.sso.SSOUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
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
            log.info("{} method:{} {} 请求参数{}，返回:{}",
                    SSOUtil.getUserName(), method.getName(), printLog.description(),
                    getArgsString(joinPoint.getArgs()),
                    returnValue.toString());
        } catch (Exception e) {
            log.error("打印日志异常", e);
        }
    }

    @AfterThrowing(value = "printLogAspect()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        try {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            PrintLog printLog = method.getAnnotation(PrintLog.class);
            log.info("{} method:{} {}  args : {}",
                    SSOUtil.getUserName(), method.getName(),
                    printLog.description() + "失败", getArgsString(joinPoint.getArgs()));
        } catch (Exception e) {
            log.error("打印日志异常", e);
        }

    }

    /**
     * 打印方法入参，过滤掉request response
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
            log.error("打印入参失败:{}", e);
        }
        return "";
    }

}
