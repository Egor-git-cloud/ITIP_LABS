package org.example.service;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class NotificationManager {

    private final List<MessageService> services;

    private final Map<String, MessageService> serviceMap;

    public NotificationManager(List<MessageService> services, Map<String, MessageService> serviceMap) {
        this.services = services;
        this.serviceMap = serviceMap;
    }

    public void notify(String message, String recipient) {
        notifyAll(message, recipient);
    }

    public void notifyAll(String message, String recipient) {
        System.out.println("--- Рассылка по всем каналам ---");
        services.forEach(service -> service.sendMessage(message, recipient));
    }

    public void notifyByType(String type, String message, String recipient) {
        MessageService service = serviceMap.get(type);
        if (service != null) {
            System.out.println("--- Отправка через выбранный тип: " + type + " ---");
            service.sendMessage(message, recipient);
        } else {
            System.out.println("Ошибка: Сервис с именем '" + type + "' не найден!");
        }
    }


}