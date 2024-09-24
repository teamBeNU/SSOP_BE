package SSOP.ssop.domain.notification;

import SSOP.ssop.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String title;

    private String card_name;

    private boolean accepted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    // 기본 생성자
    public Notification() {}

    // 사용자 지정 생성자
    public Notification(String title, String card_name, User user) {
        this.title = title;
        this.card_name = card_name;
        this.user = user;
        this.accepted = false;
    }
}
