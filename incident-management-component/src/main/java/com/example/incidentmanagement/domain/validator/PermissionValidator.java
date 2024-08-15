package com.example.incidentmanagement.domain.validator;

import com.example.incidentmanagement.common.DataAuthUtil;
import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

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
            log.info("操作人:{}是超级管理员，无需校验权限", operator);
            return;
        }
        if (incidentId != null && incidentId != 0) {
            // 不是超级管理员，且操作人和创建人不是一个人， 就不能操作
            if (!StringUtils.equals(operator, dbCreator)) {
                log.info("编辑事件id:{}的创建人:{}与本次操作人:{}不一致", incidentId, dbCreator, operator);
                throw PlatformErrorCode.PARAM_ERROR.toException("操作人:{}没有该事件的编辑权限", operator);
            }
        }
    }
}
