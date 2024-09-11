package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardShareRequest {
    private Long cardId;      // 공유할 카드의 ID
    private Long recipientId; // 수신자의 ID
}
