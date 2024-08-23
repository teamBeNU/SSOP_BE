package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.dto.NotificationDto;
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

    // @param user_name 알림을 가져올 사용자의 이름
    // @return 사용자에게 해당하는 알림 목록
    public List<Notification> getNotificationsForUser(String user_name) {
        User user = userRepository.findByUsername(user_name).orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUser(user);
    }

     // @param id - 수락할 알림의 ID
     // @param user_name - 알림을 수락할 사용자의 이름
     // @return 업데이트된 알림 객체
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

    // @param id - 거부할 알림의 ID
    // @param user_name - 알림을 거부할 사용자의 이름
    @Transactional
    public void refuseNotification(Long id, String user_name) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getUser_name().equals(user_name)) {
            throw new RuntimeException("Unauthorized");
        }

        // 알림 삭제
        notificationRepository.delete(notification);
    }
}
