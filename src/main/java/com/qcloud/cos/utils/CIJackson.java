/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.

 * According to cos feature, we modify some class，comment, field name, etc.
 */


package com.qcloud.cos.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.qcloud.cos.internal.CosServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public enum CIJackson {
    ;
    private static final Logger LOG = LoggerFactory.getLogger(CIJackson.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept(getFieldsToFilter(CosServiceRequest.class));
        FilterProvider filters = new SimpleFilterProvider().addFilter("CosServiceFilter", filter);
        objectMapper.setFilterProvider(filters);
    }
    private static final ObjectWriter writer = objectMapper.writer();

    private static Set<String> getFieldsToFilter(Class cosServiceRequestClass) {
        Set<String> fieldNames = new HashSet<>();
        Field[] declaredFields = cosServiceRequestClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            String fieldNameCapitalized = capitalizeFirstLetter(name);
            fieldNames.add(fieldNameCapitalized);
        }
        fieldNames.add("ReadLimit");
        fieldNames.add("CloneRoot");
        fieldNames.add("GeneralProgressListener");
        fieldNames.add("RequestId");
        fieldNames.add("BucketName");
//        fieldNames.add("AppId");
        return fieldNames;
    }

    public static String toJsonString(Object value) {
        try {
            return writer.writeValueAsString(value);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static byte[] toJsonBytes(Object value) {
        try {
            return writer.writeValueAsString(value).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}

