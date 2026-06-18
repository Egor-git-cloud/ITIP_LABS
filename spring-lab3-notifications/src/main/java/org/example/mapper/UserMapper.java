package org.example.mapper;
import java.util.List;
import java.util.stream.Collectors;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    /**
     * Преобразует сущность User в UserDto
     */
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setTelegramChatId(user.getTelegramChatId());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRole(user.getRole().name());  // Преобразуем enum в строку
        return dto;
    }

    /**
     * Преобразует список пользователей в список UserDto
     */
    public List<UserDto> toDtoList(List<User> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }
}