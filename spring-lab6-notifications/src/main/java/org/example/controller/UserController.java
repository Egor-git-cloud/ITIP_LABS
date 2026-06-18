package org.example.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/add")
    public UserDto createUser(@RequestBody @Valid UserDto request) {
        User response = userService.createUser(request);
        return UserDto.builder()
                .name(response.getName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .telegramChatId(response.getTelegramChatId())
                .deviceToken(response.getDeviceToken())
                .createdAt(response.getCreatedAt())
                .build();
    }
    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(Principal principal) {
        return ResponseEntity.ok("Привет, " + principal.getName());
    }
    @GetMapping("/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(r-> UserDto.builder()
                        .name(r.getName())
                        .email(r.getEmail())
                        .phone(r.getPhone())
                        .telegramChatId(r.getTelegramChatId())
                        .deviceToken(r.getDeviceToken())
                        .createdAt(r.getCreatedAt())
                        .build())
                .toList();
    }
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        User response = userService.getUserById(id);
        return UserDto.builder()
                .name(response.getName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .telegramChatId(response.getTelegramChatId())
                .deviceToken(response.getDeviceToken())
                .createdAt(response.getCreatedAt())
                .build();
    }
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody @Valid
    UserDto request) {
        User response = userService.updateUser(id, request);
        return UserDto.builder()
                .name(response.getName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .telegramChatId(response.getTelegramChatId())
                .deviceToken(response.getDeviceToken())
                .createdAt(response.getCreatedAt())
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return String.format("Пользователь %s удален", id);
    }
}