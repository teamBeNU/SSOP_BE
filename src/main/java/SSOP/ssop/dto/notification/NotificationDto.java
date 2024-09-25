package SSOP.ssop.dto.notification;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class NotificationDto {
    private Long notification_id;
    private String title;
    private String card_name;
    private boolean accepted;
    private LocalDateTime createdAt;


    public NotificationDto(Long notification_id, String title, String card_name, boolean accepted, LocalDateTime createdAt) {
        this.notification_id = notification_id;
        this.title = title;
        this.card_name = card_name;
        this.accepted = accepted;
        this.createdAt = createdAt;
    }
}
