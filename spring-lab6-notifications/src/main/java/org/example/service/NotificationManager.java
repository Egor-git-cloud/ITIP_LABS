package org.example.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    private final Map<String, MessageService> serviceMap;

    public NotificationManager(Map<String, MessageService> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public void notify(String type, String message, String recipient) {
        MessageService service = serviceMap.get(type);

        if (service != null) {
            service.sendMessage(message, recipient);
        } else {
            System.out.println("Сервис типа " + type + " не найден! Доступны: " + serviceMap.keySet());
        }
    }
}