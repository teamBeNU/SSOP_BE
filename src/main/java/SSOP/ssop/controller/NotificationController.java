package SSOP.ssop.controller;

import SSOP.ssop.domain.notification.Notification;
import SSOP.ssop.dto.NotificationDto;
import SSOP.ssop.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @DeleteMapping("/{id}/refuse")
    public ResponseEntity<Void> refuseNotification(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        notificationService.refuseNotification(id, username);
        return ResponseEntity.noContent().build();
    }
}
