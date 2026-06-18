package org.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.NotificationDto;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        return notificationService.createNotification(request);
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id, @RequestBody @Valid NotificationDto request) {
        return notificationService.updateNotification(id,request);
    }

    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }

    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status);
    }

    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getNotificationsByChannel(channel);
    }

    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId);
    }

    @GetMapping("/search")
    public List<NotificationDto> search(@RequestParam NotificationStatus status, 
                                        @RequestParam NotificationChannel channel) {
        return notificationService.getByStatusAndChannel(status, channel);
    }

    @GetMapping("/sorted")
    public List<NotificationDto> getSorted() {
        return notificationService.getAllSortedByDate();
    }
    
    @GetMapping("/user-filter")
    public List<NotificationDto> getByUserAndStatus(@RequestParam Long userId, 
                                                    @RequestParam NotificationStatus status) {
        return notificationService.getNotificationsByUserAndStatus(userId, status);
    }
}