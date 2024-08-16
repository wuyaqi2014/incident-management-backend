package com.example.incidentmanagement;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-16
 */

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
@EnableAsync
public class ServiceConfiguration {
}