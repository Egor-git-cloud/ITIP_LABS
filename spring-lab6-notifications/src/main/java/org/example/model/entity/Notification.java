package org.example.model.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import java.time.LocalDateTime;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @PrePersist
    @PreUpdate
    public void ensureSentAtIsSet() {
        if (this.status == NotificationStatus.SENT && this.sentAt == null) {
            this.sentAt = LocalDateTime.now();
        }

        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationChannel channel;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;
    private LocalDateTime createdAt;
    @CreationTimestamp
    @Column(name = "sent_at", nullable = false, updatable = true)
    private LocalDateTime sentAt;

    @Column(name = "recipient_id")
    private Long recipientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private User recipient;
}