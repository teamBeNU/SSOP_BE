package SSOP.ssop.domain.link;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LinkShare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;  // 고유 링크 토큰
    private Long cardId;   // 공유된 카드 ID
    private LocalDateTime expiryTime;  // 만료 시간

    public LinkShare(String token, Long cardId, LocalDateTime expiryTime) {
        this.token = token;
        this.cardId = cardId;
        this.expiryTime = expiryTime;
    }
}
