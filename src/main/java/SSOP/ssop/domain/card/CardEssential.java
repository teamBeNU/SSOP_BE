package SSOP.ssop.domain.card;

public class CardEssential {
    private String card_name;
    private String card_introduction;

    public CardEssential(String card_name, String card_introduction) {
        this.card_name = card_name;
        this.card_introduction = card_introduction;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

    public void setCard_introduction(String card_introduction) {
        this.card_introduction = card_introduction;
    }
}
