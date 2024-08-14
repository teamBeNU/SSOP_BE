package SSOP.ssop.domain.card;

public class CardOptional {
    private String card_SNS;
    private String card_email;
    private String card_MBTI;
    private String card_music;

    public CardOptional(String card_SNS, String card_email, String card_MBTI, String card_music) {
        this.card_SNS = card_SNS;
        this.card_email = card_email;
        this.card_MBTI = card_MBTI;
        this.card_music = card_music;
    }

    public String getCard_SNS() {
        return card_SNS;
    }

    public void setCard_SNS(String card_SNS) {
        this.card_SNS = card_SNS;
    }

    public String getCard_email() {
        return card_email;
    }

    public void setCard_email(String card_email) {
        this.card_email = card_email;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public void setCard_MBTI(String card_MBTI) {
        this.card_MBTI = card_MBTI;
    }

    public String getCard_music() {
        return card_music;
    }

    public void setCard_music(String card_music) {
        this.card_music = card_music;
    }
}
