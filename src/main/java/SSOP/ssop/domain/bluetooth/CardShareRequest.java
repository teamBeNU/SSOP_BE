package SSOP.ssop.domain.bluetooth;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CardShareRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cardId;
    private Long recipientId;
    private String status;

    public CardShareRequest() {
        this.id = id;
        this.cardId = cardId;
        this.recipientId = recipientId;
        this.status = status;
    }
}
