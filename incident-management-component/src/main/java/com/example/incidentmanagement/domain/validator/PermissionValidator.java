package com.example.incidentmanagement.domain.validator;

import org.apache.commons.lang3.StringUtils;

import com.example.incidentmanagement.common.DataAuthUtil;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Slf4j
@Builder
public class PermissionValidator implements Validator {

    private Long incidentId;

    private String operator;

    private String dbCreator;
    @Override
    public void validate() {
        if (DataAuthUtil.isSupreme(operator)) {
            log.info("Operator:{} is a supreme administrator，no need to check permissions", operator);
            return;
        }
        if (incidentId != null && incidentId != 0) {
            // Not a supreme administrator and the operator is not the same person as the creator, so they cannot operate
            if (!StringUtils.equals(operator, dbCreator)) {
                log.info("The creator :{} of the incident id: {} is not consistent with the current operator: {}",
                        incidentId, dbCreator, operator);
                throw PlatformErrorCode.PARAM_ERROR
                        .toException("Operator: {} does not have permission to edit this incident", operator);
            }
        }
    }
}
