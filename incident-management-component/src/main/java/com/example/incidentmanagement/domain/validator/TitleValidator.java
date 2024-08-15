package com.example.incidentmanagement.domain.validator;

import org.apache.commons.lang3.StringUtils;

import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.persisitence.entity.Incident;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-14
 */
@Slf4j
@Builder
public class TitleValidator implements Validator{

    private String title;

    private String operator;

    private IncidentRepository incidentRepository;

    private Long incidentId;

    private static final int MAX_NAME_LENGTH = 100;

    @Override
    public void validate() {
        if (StringUtils.isBlank(title)) {
            throw  PlatformErrorCode.PARAM_ERROR.toException("title不能为空");
        }
        log.info("title:{}", title);
        if (title.length() > MAX_NAME_LENGTH * 2) {
            throw PlatformErrorCode.PARAM_ERROR.toException("title不能大于100个字符");
        }
        // 同一用户下，title不能重复
       Incident incidentDB = incidentRepository.queryIncidentByOperator(operator, title);
        if (incidentId == null) { // 新建
            if (incidentDB != null) {
                throw PlatformErrorCode.PARAM_ERROR.toException("同一用户下，title不能重复");
            }
        } else { // 编辑
            if (incidentDB != null && incidentDB.getId() != null && !incidentDB.getId().equals(incidentId)) {
                throw PlatformErrorCode.PARAM_ERROR.toException("同一用户下，title不能重复");
            }
        }
    }

}
