package SSOP.ssop.controller.notification;

import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.dto.notification.NotificationDto;
import SSOP.ssop.dto.notification.NotificationRequestDto;
import SSOP.ssop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 사용자 알림 목록 반환
    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(Authentication authentication) {
        String username = authentication.getName();
        List<Notification> notifications = notificationService.getNotificationsForUser(username);
        List<NotificationDto> response = notifications.stream()
                .map(notification -> new NotificationDto(
                        notification.getId(),
                        notification.getTitle(),
                        notification.getCard_name(),
                        notification.isAccepted()
                )).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // 알림 생성
    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationRequestDto request, Authentication authentication) {
        // 알림 생성 서비스 호출
        Notification notification = notificationService.createNotification(authentication, request.getTitle(), request.getCard_name());

        // 응답 DTO 생성
        NotificationDto response = new NotificationDto(
                notification.getId(),
                notification.getTitle(),
                notification.getCard_name(),
                notification.isAccepted()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    // 알림 수락
    @PostMapping("/{id}/accept")
    public ResponseEntity<NotificationDto> acceptNotification(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        Notification notification = notificationService.acceptNotification(id, username);
        NotificationDto response = new NotificationDto(
                notification.getId(),
                notification.getTitle(),
                notification.getCard_name(),
                notification.isAccepted()
        );
        return ResponseEntity.ok(response);
    }

    // 알림 거절
    @DeleteMapping("/{id}/refuse")
    public ResponseEntity<Void> refuseNotification(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        notificationService.refuseNotification(id, username);
        return ResponseEntity.noContent().build();
    }
}
