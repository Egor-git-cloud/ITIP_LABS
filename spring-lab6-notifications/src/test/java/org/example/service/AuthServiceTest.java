package org.example.service;

import java.util.Optional;

import org.example.mapper.UserMapper;
import org.example.model.dto.RegisterRequest;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private AuthService authService;
    @Test
    void registerUserTest() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Egor");
        request.setEmail("egor@example.com");
        request.setPassword("securePass123");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword123");
        authService.register(request);

        verify(passwordEncoder, times(1)).encode("securePass123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerAdminTest() {
        RegisterRequest request = new RegisterRequest();
        request.setName("AdminEgor");
        request.setEmail("admin@example.com");
        request.setPassword("adminPass");
        request.setPhone("+79991112233");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedAdminPass");
        authService.registerAdmin(request);

        verify(passwordEncoder, times(1)).encode("adminPass");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserProfileTest() {
        String email = "egor@example.com";
        
        User user = new User();
        user.setEmail(email);
        user.setRole(UserRole.ROLE_USER);

        UserDto expectedDto = new UserDto();
        expectedDto.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        UserDto resultDto = authService.getUserProfile(email);

        assertNotNull(resultDto);
        assertEquals(email, resultDto.getEmail());

        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toDto(user);
    }
}