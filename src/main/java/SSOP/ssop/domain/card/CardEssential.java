package SSOP.ssop.domain.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardEssential {
    private String card_name;
    private String card_introduction;

    public CardEssential(String card_name, String card_introduction) {
        this.card_name = card_name;
        this.card_introduction = card_introduction;
    }
}
