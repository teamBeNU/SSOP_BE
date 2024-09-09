package SSOP.ssop.dto.card.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CardShareResponse {
    private Long cardId;
    private Long recipientId;
    private String status;
}
