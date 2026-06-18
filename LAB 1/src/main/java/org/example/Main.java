package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Person;
import org.example.util.JsonUtils;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
        logger.info("=== Лабораторная работа: Maven ===");
        
        // Демонстрация логирования
        demonstrateLogging();
        
        // Демонстрация JSON
        demonstrateJson();
        
        logger.info("=== Программа завершена ===");
    }
    
    private static void demonstrateLogging() {
        logger.info("--- Демонстрация логирования ---");
        logger.debug("Уровень DEBUG");
        logger.info("Уровень INFO");
        logger.warn("Уровень WARN");
        logger.error("Уровень ERROR");
    }
    
    private static void demonstrateJson() {
        logger.info("--- Демонстрация JSON ---");
        
        Person person = new Person("Иван Петров", 25, "ivan@email.com");
        logger.info("Создан объект: {}", person);
        
        String json = JsonUtils.toJson(person);
        logger.info("JSON: {}", json);
        
        Person personFromJson = JsonUtils.fromJson(json);
        logger.info("Восстановленный объект: {}", personFromJson);
    }
}