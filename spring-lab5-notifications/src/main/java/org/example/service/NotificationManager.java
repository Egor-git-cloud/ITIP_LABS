package org.example.service;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {

    private final Map<String, MessageService> serviceMap;

    @Autowired
    public NotificationManager(Map<String, MessageService> serviceMap) {
        this.serviceMap = serviceMap;
    }
    public void notify(String message, String recipient, String serviceType) {
        MessageService service = serviceMap.get(serviceType);

        if (service != null) {
            service.sendMessage(message, recipient);
        } else {
            System.out.println("Сервис '" + serviceType + "' не найден! Доступные: " + serviceMap.keySet());
        }
    }
}
