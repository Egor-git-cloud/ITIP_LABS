package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Person;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static String toJson(Person person) {
        try {
            String json = objectMapper.writeValueAsString(person);
            logger.info("Сериализован объект: {}", json);
            return json;
        } catch (Exception e) {
            logger.error("Ошибка сериализации", e);
            return null;
        }
    }
    
    public static Person fromJson(String json) {
        try {
            Person person = objectMapper.readValue(json, Person.class);
            logger.info("Десериализован объект: {}", person);
            return person;
        } catch (Exception e) {
            logger.error("Ошибка десериализации", e);
            return null;
        }
    }
}