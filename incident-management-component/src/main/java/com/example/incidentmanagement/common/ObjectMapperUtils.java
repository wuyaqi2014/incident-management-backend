package com.example.incidentmanagement.common;

import javax.annotation.Nullable;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

/**
 * @author wuyaqi <wuyaqi_2014@qq.com>
 * Created on 2024-08-14
 */

public final class ObjectMapperUtils {

    private static final ObjectMapper MAPPER;

    public ObjectMapperUtils() {
    }

    public static String toJSON(@Nullable Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }
    }


    static {
        MAPPER = new ObjectMapper((new JsonFactory()).disable(JsonFactory.Feature.INTERN_FIELD_NAMES));
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(new JsonParser.Feature[]{com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS});
        MAPPER.enable(new JsonParser.Feature[]{com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS});
        MAPPER.registerModule(new ParameterNamesModule());
    }
}