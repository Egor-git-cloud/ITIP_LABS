package org.example.repository;

import org.example.model.entity.Notification;
import org.example.model.entity.User;
import org.example.model.enums.NotificationStatus;
import org.example.model.enums.NotificationChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByStatus(NotificationStatus status);

    List<Notification> findByChannel(NotificationChannel channel);

    List<Notification> findByRecipientId(Long recipientId);

    List<Notification> findByStatusAndChannel(NotificationStatus status, NotificationChannel channel);

    List<Notification> findAllByOrderByCreatedAtDesc();

    @Query("SELECT n FROM Notification n WHERE n.recipient.id = :recipientId AND n.status = :status")
    List<Notification> findByRecipientIdAndStatus(@Param("recipientId") Long recipientId,
                                                  @Param("status") NotificationStatus status);

    @Query(value = "SELECT * FROM notifications ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<Notification> findTop5RecentNative();
}