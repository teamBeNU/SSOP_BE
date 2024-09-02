package SSOP.ssop.service.Notification;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.repository.Notification.NotificationRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        User user = userRepository.findByUser_name(user_name).orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findByUser(user);
    }

    // 알림 생성
    public Notification createNotification(Authentication authentication, String title, String cardName) {
        // 인증된 사용자의 이름을 가져옴
        String username = authentication.getName();

        // username을 기반으로 사용자 객체를 찾음
        User user = userRepository.findByUser_name(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // 새로운 알림 생성
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setCard_name(cardName);
        notification.setAccepted(false);

        // 알림 저장
        return notificationRepository.save(notification);
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
