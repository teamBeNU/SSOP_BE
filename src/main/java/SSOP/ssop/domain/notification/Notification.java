package SSOP.ssop.domain.notification;

import SSOP.ssop.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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

    private LocalDateTime createdAt; // 생성 시간 필드 추가

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
        this.createdAt = LocalDateTime.now();
        this.accepted = false;
    }
}
