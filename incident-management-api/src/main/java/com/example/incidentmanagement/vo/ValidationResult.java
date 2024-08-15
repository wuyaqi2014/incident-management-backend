package com.example.incidentmanagement.vo;

import lombok.Data;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-15
 */
@Data
public class ValidationResult {

    private boolean hasError;

    private String errorMsg;

}
