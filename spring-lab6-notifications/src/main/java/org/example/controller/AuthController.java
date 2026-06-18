package org.example.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mapper.UserMapper;
import org.example.model.dto.RegisterRequest;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.example.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
        try {
            authService.registerAdmin(request);
            return ResponseEntity.ok("Admin registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RestController
    @RequestMapping("/test")
    public class TestController {

        private final UserMapper userMapper;

        public TestController(UserMapper userMapper) {
            this.userMapper = userMapper;
        }

        @GetMapping("/mapper")
        public UserDto checkMapper() {
            User user = new User();
            user.setName("Ivanov");
            user.setEmail("ivanov@test.com");
            user.setPhone("+79990000000");

            return userMapper.toDto(user);
        }
    }
}
