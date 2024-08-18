package SSOP.ssop.repository;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUser(User user);
}
