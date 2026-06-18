package org.example.repository;

import org.example.model.entity.Notification;
import org.example.model.enums.NotificationChannel;
import org.example.model.enums.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // ===== Базовые методы =====
    
    // Поиск по статусу
    List<Notification> findByStatus(NotificationStatus status);
    
    // Поиск по каналу
    List<Notification> findByChannel(NotificationChannel channel);
    
    // Поиск по ID получателя
    List<Notification> findByRecipientId(Long recipientId);
    
    // ===== 6.1. Запрос по нескольким параметрам =====
    
    // Поиск по статусу И каналу одновременно
    List<Notification> findByStatusAndChannel(NotificationStatus status, NotificationChannel channel);
    
    // ===== 6.2. Сортировка в имени метода =====
    
    // Поиск по статусу с сортировкой по дате создания (по возрастанию)
    List<Notification> findByStatusOrderByCreatedAtAsc(NotificationStatus status);
    
    // Поиск по статусу с сортировкой по дате создания (по убыванию)
    List<Notification> findByStatusOrderByCreatedAtDesc(NotificationStatus status);
    
    // ===== 6.3. Использование @Query (JPQL) =====
    // JPQL запрос - работает с именами Java-классов и полей
    @Query("SELECT n FROM Notification n WHERE n.status = :status AND n.channel = :channel")
    List<Notification> findByStatusAndChannelCustom(@Param("status") NotificationStatus status, 
                                                     @Param("channel") NotificationChannel channel);
    
    // Native SQL запрос - работает с именами таблиц и колонок в БД
    @Query(value = "SELECT * FROM notifications WHERE status = :status AND channel = :channel", 
           nativeQuery = true)
    List<Notification> findNativeByStatusAndChannel(@Param("status") String status, 
                                                     @Param("channel") String channel);
    // ===== Дополнительные методы для самостоятельных заданий =====
    
    // Поиск уведомлений пользователя по его recipientId и статусу
    @Query("SELECT n FROM Notification n WHERE n.recipient.id = :recipientId AND n.status = :status")
    List<Notification> findByRecipientIdAndStatus(@Param("recipientId") Long recipientId, 
                                                   @Param("status") NotificationStatus status);
}