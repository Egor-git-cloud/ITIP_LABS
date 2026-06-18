package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.example.repository.NotificationRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationDto mapToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setChannel(notification.getChannel());
        dto.setStatus(notification.getStatus());
        dto.setRecipientId(notification.getRecipient().getId());

        return dto;
    }

    @Transactional
    public Notification createNotification(NotificationDto request) {

        User user = userRepository.findById(request.getRecipientId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);
        return notificationRepository.save(notification);
    }

    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<NotificationDto> getNotificationsByStatusAndChannel(NotificationStatus status, NotificationChannel channel) {
        return notificationRepository.findByStatusAndChannel(status, channel).stream()
                .map(this::mapToDto)
                .toList();
    }
    //добавил для usage List<Notification> findByStatusAndChannel(NotificationStatus status, NotificationChannel channel); (3)

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Notification updateNotification(Long id, NotificationDto request) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setChannel(request.getChannel());


        if (request.getStatus() == NotificationStatus.SENT && notification.getStatus() != NotificationStatus.SENT) {
            notification.setSentAt(LocalDateTime.now());
        }

        notification.setStatus(request.getStatus());
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notificationRepository.delete(notification);
    }

    @Transactional
    public void markAsSent(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setStatus(NotificationStatus.SENT);
        notification.setSentAt(LocalDateTime.now());
    }

    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    public List<Notification> getNotificationsByChannel(NotificationChannel channel) {
        return notificationRepository.findByChannel(channel);
    }

    public List<Notification> getNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    public List<NotificationDto> getNotificationsByRecipientAndStatus(Long recipientId, NotificationStatus status) {
        return notificationRepository.findByRecipientIdAndStatus(recipientId, status).stream()
                .map(this::mapToDto)
                .toList();
    }
}