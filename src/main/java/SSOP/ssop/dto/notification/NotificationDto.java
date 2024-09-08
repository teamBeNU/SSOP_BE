package SSOP.ssop.dto.notification;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
    private Long notification_id;
    private String title;
    private String card_name;
    private boolean accepted;


    public NotificationDto(Long notification_id, String title, String card_name, boolean accepted) {
        this.notification_id = notification_id;
        this.title = title;
        this.card_name = card_name;
        this.accepted = accepted;
    }
}
