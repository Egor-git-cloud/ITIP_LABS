package org.example.service;
import java.util.Optional;

import org.example.model.dto.NotificationDto;
import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationChannel;
import org.example.repository.NotificationRepository;
import org.example.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private NotificationService notificationService;
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        NotificationDto dto = NotificationDto.builder()
                .title("Напоминание")
                .message("Сообщение")
                .channel(NotificationChannel.EMAIL)
                .recipientId(99L)
                .build();
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                notificationService.createNotification(dto));
    }

    @Test
    void shouldCreateNotification() {
        User user = new User();
        user.setId(1L);
        user.setEmail("ivan@example.com");
        NotificationDto dto = NotificationDto.builder()
                .title("Напоминание")
                .message("Завтра пара по Spring")
                .channel(NotificationChannel.EMAIL)
                .recipientId(1L)
                .build();

        Notification savedNotification = new Notification();
        savedNotification.setTitle(dto.getTitle());
        savedNotification.setMessage(dto.getMessage());
        savedNotification.setChannel(dto.getChannel());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepository.save(any(Notification.class))).thenReturn(savedNotification);
        Notification result = notificationService.createNotification(dto);
        assertNotNull(result);
        assertEquals("Напоминание", result.getTitle());
        assertEquals(NotificationChannel.EMAIL, result.getChannel());
    }

    @Test
    void shouldReturnNotificationWhenExists() {
        Long id = 1L;
        Notification mockNotification = new Notification();
        mockNotification.setId(id);

        when(notificationRepository.findById(id)).thenReturn(Optional.of(mockNotification));

        Notification result = notificationService.getNotificationById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(notificationRepository, times(1)).findById(id);
    }


    @Test
    void shouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(notificationRepository.findById(id)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> notificationService.getNotificationById(id));
    }
}
