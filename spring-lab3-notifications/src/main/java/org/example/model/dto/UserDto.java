package org.example.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private String name;
    private String email;
    private String phone;
    private String deviceToken;
    private String telegramChatId;
}