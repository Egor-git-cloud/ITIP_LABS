package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.repository.NotificationRepository;
import java.time.LocalDateTime;

import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Уведомление не найдено"));
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatusOrderByCreatedAtAsc(status);
    }

    public List<Notification> getNotificationsByChannel(NotificationChannel channel) {
        return notificationRepository.findByChannel(channel);
    }

    public List<Notification> getNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }
    public List<Notification> getByStatusAndChannel(NotificationStatus status, NotificationChannel channel) {
        return notificationRepository.findByStatusAndChannel(status, channel);
    }
    public List<Notification> getAllNotificationsSorted() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }
    public List<Notification> getByRecipientAndStatus(Long recipientId, NotificationStatus status) {
        return notificationRepository.findByRecipientAndStatus(recipientId, status);
    }
    public Notification updateNotificationStatus(Long id, NotificationStatus newStatus) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Уведомление не найдено"));

        notification.setStatus(newStatus);

        if (newStatus == NotificationStatus.SENT) {
            notification.setSentAt(LocalDateTime.now());
        }

        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification createNotification(NotificationDto dto) {
        User user = userRepository.findById(dto.getRecipientId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        notification.setChannel(dto.getChannel());
        notification.setStatus(dto.getStatus());
        notification.setRecipient(user);

        notification.setRecipientId(dto.getRecipientId());

        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification updateNotification(Long id, NotificationDto dto) {
        Notification notification = getNotificationById(id);
        notification.setTitle(dto.getTitle());
        notification.setMessage(dto.getMessage());
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        if (!notificationRepository.existsById(id)) {
            throw new RuntimeException("Нечего удалять");
        }
        notificationRepository.deleteById(id);
    }

}