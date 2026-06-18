package org.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.model.enums.NotificationChannel;

@Getter
@Setter
@Builder
public class NotificationDto {
    private String title;
    private String message;
    private NotificationChannel channel;
    private Long recipientId;
}