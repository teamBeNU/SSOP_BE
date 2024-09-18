package SSOP.ssop.dto.card.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardSaveResponse {
    private boolean isAdded;
    private String message;

    public CardSaveResponse(boolean isAdded, String message) {
        this.isAdded = isAdded;
        this.message = message;
    }
}
