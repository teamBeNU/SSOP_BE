package SSOP.ssop.repository.Notification;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

//    // 특정 사용자의 알림 중 7일이 지나지 않은 알림만 가져오기
//    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId AND n.createdAt >= CURRENT_DATE - 7 ORDER BY n.createdAt DESC")
//    List<Notification> findRecentNotificationsByUserId(@Param("userId") Long userId);

    // 특정 사용자의 모든 알림 조회
    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId")
    List<Notification> findRecentNotificationsByUserId(@Param("userId") long userId);

    // 7일이 지난 알림 삭제
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.createdAt < :cutoffDate")
    void deleteNotificationsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}


