package com.example.incidentmanagement.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;

import com.alibaba.fastjson2.JSON;
import com.example.incidentmanagement.param.IncidentParam;
import com.example.incidentmanagement.vo.ValidationResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wuyaqi <wuyaqi@kuaishou.com>
 * Created on 2024-08-15
 */
@Slf4j
public class CustomerValidator {

    private static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    public static <T> Set<ConstraintViolation<T>> validateEntity(T obj) {
        return validator.validate(obj);
    }

    public static ValidationResult validateParam(IncidentParam incidentParam) {
        Set<ConstraintViolation<IncidentParam>> incidentViolationSet = validateEntity(incidentParam);
        ValidationResult validationResult = new ValidationResult();
        if (!incidentViolationSet.isEmpty()) {
            validationResult.setHasError(true);
            validationResult.setErrorMsg(errorMsg(incidentViolationSet));
            return validationResult;
        }

        log.info("注解参数校验结果:{}", JSON.toJSONString(validationResult));
        return validationResult;
    }

    public static <T> String errorMsg(Set<ConstraintViolation<T>> validateSet) {
        StringBuilder errorMsg = new StringBuilder();
        for (ConstraintViolation<T> violation : validateSet) {
            log.info("message{} ", violation.getMessage());
            errorMsg.append(violation.getMessage()).append(";");
        }
        return StringUtils.isBlank(errorMsg.toString()) ? "OK" : errorMsg.toString();
    }
}
