package SSOP.ssop.domain.card;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class CardSaveDetails {

    private String source;
    private LocalDateTime savedTime;

    public CardSaveDetails() {}

    public CardSaveDetails(String source, LocalDateTime savedTime) {
        this.source = source;
        this.savedTime = savedTime;
    }
}
