package com.example.incidentmanagement.domain.validator;

import org.apache.commons.lang3.StringUtils;

import com.example.incidentmanagement.common.enums.PlatformErrorCode;
import com.example.incidentmanagement.domain.repository.IncidentRepository;
import com.example.incidentmanagement.persisitence.entity.Incident;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */
@Slf4j
@Builder
public class TitleValidator implements Validator {

    private String title;

    private String operator;

    private IncidentRepository incidentRepository;

    private Long incidentId;

    private static final int MAX_NAME_LENGTH = 100;

    @Override
    public void validate() {
        if (StringUtils.isBlank(title)) {
            throw PlatformErrorCode.PARAM_ERROR.toException("Title cannot be empty");
        }
        log.info("title: {}", title);
        if (title.length() > MAX_NAME_LENGTH) {
            throw PlatformErrorCode.PARAM_ERROR.toException("Title cannot exceed 100 characters");
        }
        // The title must be unique for the same user
        Incident incidentDB = incidentRepository.queryIncidentByOperator(operator, title);
        if (incidentId == null) { // Creating new
            if (incidentDB != null) {
                throw PlatformErrorCode.PARAM_ERROR.toException("Title must be unique for the same user");
            }
        } else { // Editing existing
            if (incidentDB != null && incidentDB.getId() != null && !incidentDB.getId().equals(incidentId)) {
                throw PlatformErrorCode.PARAM_ERROR.toException("Title must be unique for the same user");
            }
        }
    }

}
