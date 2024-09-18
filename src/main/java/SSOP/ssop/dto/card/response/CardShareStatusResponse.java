package SSOP.ssop.dto.card.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CardShareStatusResponse {
    private Long cardId;
    private Long recipientId;
    private String status;

    public CardShareStatusResponse(Long cardId, long userId, String status, Long userId1) {
    }
}
