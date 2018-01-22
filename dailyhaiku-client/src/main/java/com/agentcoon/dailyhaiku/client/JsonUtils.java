package com.agentcoon.dailyhaiku.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

/**
 * Mapping from beans to Json and vice versa
 */
public class JsonUtils {
    private final ObjectMapper mapper;

    public JsonUtils() {
        mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Deserialise from Json String to bean
     *
     * @param json  raw json
     * @param clazz type of object to return
     * @return
     */
    public <T> T fromJson(String json, Class<T> clazz) {

        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new DailyHaikuClientException(e.getClass().getName(), e);
        }
    }


    /**
     * Serialise from bean into Json String
     *
     * @param src
     * @return
     */
    public String toJson(Object src) {

        try {
            return mapper.writeValueAsString(src);
        } catch (IOException e) {
            throw new DailyHaikuClientException(e.getClass().getName(), e);
        }
    }
}