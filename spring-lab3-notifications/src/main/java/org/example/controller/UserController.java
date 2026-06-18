package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.mapper.UserMapper;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;  // ДОБАВИТЬ

    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        return userMapper.toDto(user);
    }

    // НОВЫЙ МЕТОД: удаление пользователя (только для ADMIN)
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Пользователь с id " + id + " успешно удален";
    }
}