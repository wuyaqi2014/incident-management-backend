package com.example.incidentmanagement.annotation;

import java.lang.annotation.*;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrintLog {
    /**
     * 应用在方法上，描述此操作，比如编辑事件，新建事件
     * @return
     */
    String description() default "";
}