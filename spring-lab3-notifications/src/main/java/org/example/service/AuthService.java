package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.RegisterRequest;
import org.example.model.entity.User;
import org.example.model.enums.UserRole;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service  // Регистрируем как сервис в Spring
@RequiredArgsConstructor  // Создает конструктор для final полей
public class AuthService {

    private final UserRepository userRepository;  // Для работы с БД
    private final PasswordEncoder passwordEncoder;  // Для шифрования паролей

    /**
     * Регистрация нового пользователя
     * @param request данные из формы регистрации (имя, email, пароль)
     */
    public void register(RegisterRequest request) {
        // ПРОВЕРКА: существует ли пользователь с таким email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с email " + request.getEmail() + " уже существует");
        }
        
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}