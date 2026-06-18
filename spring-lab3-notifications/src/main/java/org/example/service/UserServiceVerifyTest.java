package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceVerifyTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCallSaveOnRepository() {
        UserDto dto = UserDto.builder()
            .name("Иван")
            .email("ivan@example.com")
            .build();

        when(userRepository.save(any(User.class))).thenReturn(new User());

        userService.createUser(dto);

        verify(userRepository).save(any(User.class));
    }
}