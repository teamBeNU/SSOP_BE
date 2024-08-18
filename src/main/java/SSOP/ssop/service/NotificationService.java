package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.repository.NotificationRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Notification> getNotificationsForUser(String user_name) {
        User user = userRepository.findByUsername(user_name).orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUser(user);
    }

    @Transactional
    public Notification acceptNotification(Long id, String user_name) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getUser_name().equals(user_name)) {
            throw new RuntimeException("Unauthorized");
        }

        notification.setAccepted(true);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void refuseNotification(Long id, String user_name) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getUser_name().equals(user_name)) {
            throw new RuntimeException("Unauthorized");
        }

        notificationRepository.delete(notification);
    }
}
