package org.example.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.service.NotificationService;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import java.util.List;
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/add")
    public NotificationDto createNotification(@RequestBody @Valid NotificationDto request) {
        Notification entity = notificationService.createNotification(request);
        return mapToDto(entity);
    }
    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public NotificationDto getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return mapToDto(notification);
    }
    @PutMapping("/{id}")
    public NotificationDto updateNotification(@PathVariable Long id, @RequestBody @Valid NotificationDto request) {
        Notification response = notificationService.updateNotification(id, request);
        return mapToDto(response);
    }
    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return "Уведомление удалено";
    }
    @GetMapping("/status/{status}")
    public List<NotificationDto> getByStatus(@PathVariable NotificationStatus status) {
        return notificationService.getNotificationsByStatus(status).stream()
                .map(this::mapToDto)
                .toList();
    }
    @GetMapping("/channel/{channel}")
    public List<NotificationDto> getByChannel(@PathVariable NotificationChannel channel) {
        return notificationService.getNotificationsByChannel(channel).stream()
                .map(this::mapToDto)
                .toList();
    }
    @GetMapping("/recipient/{recipientId}")
    public List<NotificationDto> getByRecipientId(@PathVariable Long recipientId) {
        return notificationService.getNotificationsByRecipientId(recipientId).stream()
                .map(this::mapToDto)
                .toList();
    }
    @GetMapping("/filter")
    public List<NotificationDto> getByStatusAndChannel(
            @RequestParam NotificationStatus status,
            @RequestParam NotificationChannel channel) {
        return notificationService.getByStatusAndChannel(status, channel).stream()
                .map(this::mapToDto)
                .toList();
    }
    @GetMapping("/sorted")
    public List<NotificationDto> getAllSorted() {
        return notificationService.getAllNotificationsSorted().stream()
                .map(this::mapToDto)
                .toList();
    }
    @GetMapping("/search")
    public List<NotificationDto> searchNotifications(
            @RequestParam Long recipientId,
            @RequestParam NotificationStatus status) {
        return notificationService.getByRecipientAndStatus(recipientId, status).stream()
                .map(this::mapToDto)
                .toList();
    }
    private NotificationDto mapToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setStatus(notification.getStatus());
        dto.setChannel(notification.getChannel());
        dto.setRecipientId(notification.getRecipientId());
        dto.setCreatedAt(notification.getCreatedAt());

        dto.setSentAt(notification.getSentAt());

        return dto;
    }
}