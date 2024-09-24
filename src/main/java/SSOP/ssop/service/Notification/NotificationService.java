package SSOP.ssop.service.Notification;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.repository.Notification.NotificationRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository; // 유저를 찾기 위한 리포지토리

    // 알림 생성
    @Transactional
    public Notification createNotification(Long userId, String title, String card_name) {
        // 사용자를 데이터베이스에서 찾음
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

        // 알림 객체 생성 및 저장, 생성 시간 추가
        Notification notification = new Notification(title, card_name, user);
        notification.setCreatedAt(new Date()); // 현재 시간을 설정
        return notificationRepository.save(notification);
    }


    // 알림 목록 조회
    @Transactional(readOnly = true)
    public List<Notification> getNotificationsForUser(Long userId) {
        // 사용자의 7일이 지나지 않은 알림을 조회
        return notificationRepository.findRecentNotificationsByUserId(userId);
    }

    // 알림 수락
    @Transactional
    public Notification acceptNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림을 찾을 수 없습니다."));

        notification.setAccepted(true);  // 알림을 수락 상태로 설정
        return notificationRepository.save(notification);  // 변경 사항 저장
    }

    // 알림 거절
    @Transactional
    public void refuseNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림을 찾을 수 없습니다."));

        notificationRepository.delete(notification);  // 알림 삭제
    }
}
