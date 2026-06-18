package org.example;

import java.io.IOException;  // Импорт нашего модуля
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

import org.example.utils.StringProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    // Метод для чтения информации о сборке
    private static Properties readBuildInfo() {
        Properties properties = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("build-passport.properties")) {
            if (input == null) {
                throw new IOException("Файл build-passport.properties не найден");
            }
            properties.load(input);
        } catch (IOException e) {
            logger.error("Ошибка при чтении файла build-passport.properties", e);
        }
        return properties;
    }

    public static void main(String[] args) {
        // Читаем информацию о сборке
        Properties buildInfo = readBuildInfo();
        
        // Выводим информацию о сборке
        logger.info("Информация о сборке:");
        logger.info("Пользователь: {}", buildInfo.getProperty("user"));
        logger.info("Операционная система: {}", buildInfo.getProperty("os"));
        logger.info("Версия Java: {}", buildInfo.getProperty("javaVersion"));
        logger.info("Время сборки: {}", buildInfo.getProperty("buildTime"));
        logger.info("Хеш коммита: {}", buildInfo.getProperty("commitHash"));
        logger.info("Сообщение: {}", buildInfo.getProperty("message"));

        logger.info("Программа запущена");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Введите строку: ");
            
            // Проверяем, есть ли следующий элемент для чтения
            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String result = StringProcessor.reverse(input);  // Используем метод из нового модуля
                
                logger.info("Обработанная строка: {}", result);
                System.out.println("Результат: " + result);
            } else {
                logger.error("Ошибка: ввод данных недоступен");
                System.err.println("Ошибка: невозможно прочитать ввод");
            }
        } catch (Exception e) {
            logger.error("Произошла ошибка при вводе данных", e);
        }
        
        logger.info("Программа завершена");
    }
}
