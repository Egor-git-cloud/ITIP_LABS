package org.example.model.entity;
import jakarta.persistence.*;
import lombok.*;
import org.example.model.enums.UserRole;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String email;
    private String phone;
    private String deviceToken;
    private String telegramChatId;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications = new ArrayList<>();
    @Column(nullable = false, length = 255)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;
    @Column(name = "username")
    private String username;

}