package SSOP.ssop.domain.link;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class LinkShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;
    private String link;
    private LocalDateTime expiryTime;

    public LinkShare() {}

    // 생성자
    public LinkShare(Long cardId, String link, LocalDateTime expiryTime) {
        this.cardId = cardId;
        this.link = link;
        this.expiryTime = expiryTime;
    }
}
