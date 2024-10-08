package SSOP.ssop.controller.notification;

import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.dto.notification.NotificationDto;
import SSOP.ssop.dto.notification.NotificationRequestDto;
import SSOP.ssop.service.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 알림 생성 API
    @PostMapping
    public ResponseEntity<?> createNotification(@RequestParam Long userId,
                                                @RequestBody NotificationRequestDto request,
                                                Authentication authentication) {
        // 알림 생성 서비스 호출
        Notification notification = notificationService.createNotification(userId, request.getTitle(), request.getCard_name());

        // 응답 DTO 생성
        NotificationDto response = new NotificationDto(
                notification.getNotificationId(),
                notification.getTitle(),
                notification.getCard_name(),
                notification.isAccepted(),
                notification.getCreatedAt()
        );

        // 201 Created 응답 반환
        return new ResponseEntity<>(Map.of(
                "message", "알림이 생성되었습니다.",
                "notification_id", notification.getNotificationId(),
                "created_at", notification.getCreatedAt()
        ), HttpStatus.CREATED);
    }

    // 알림 목록 조회 API
    @GetMapping
    public ResponseEntity<?> getNotifications(@RequestParam Long userId, Authentication authentication) {
        // 사용자의 알림 목록 조회
        List<Notification> notifications = notificationService.getNotificationsForUser(userId);

        // 응답 DTO로 변환
        List<NotificationDto> response = notifications.stream()
                .map(notification -> new NotificationDto(
                        notification.getNotificationId(),
                        notification.getTitle(),
                        notification.getCard_name(),
                        notification.isAccepted(),
                        notification.getCreatedAt()
                ))
                .collect(Collectors.toList());

        // 200 OK 응답 반환
        return ResponseEntity.ok(response);
    }

    // 알림 수락 API
    @PostMapping("/{notificationId}/accept")
    public ResponseEntity<?> acceptNotification(@PathVariable("notificationId") Long notificationId, Authentication authentication) {
        // 알림 수락 서비스 호출
        Notification notification = notificationService.acceptNotification(notificationId);

        // 응답 DTO 생성
        NotificationDto response = new NotificationDto(
                notification.getNotificationId(),
                notification.getTitle(),
                notification.getCard_name(),
                notification.isAccepted(),
                notification.getCreatedAt()
        );

        // 200 OK 응답 반환
        return ResponseEntity.ok(response);
    }

    // 알림 거절 API
    @DeleteMapping("/{notificationId}/refuse")
    public ResponseEntity<?> refuseNotification(@PathVariable("notificationId") Long notificationId, Authentication authentication) {
        // 알림 거절 서비스 호출
        notificationService.refuseNotification(notificationId);

        return ResponseEntity.ok(Map.of("message", "알람이 거절되었습니다."));
    }
}
