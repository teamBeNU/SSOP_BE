package SSOP.ssop.domain.card;

public class CardOptional {
    private SNS card_SNS;
    private String card_email;
    private String card_MBTI;
    private Music card_music;
    private String card_movie;

    public CardOptional(SNS card_SNS, String card_email, String card_MBTI, Music card_music, String card_movie) {
        this.card_SNS = card_SNS;
        this.card_email = card_email;
        this.card_MBTI = card_MBTI;
        this.card_music = card_music;
        this.card_movie = card_movie;
    }

    public SNS getCard_SNS() {
        return card_SNS;
    }

    public void setCard_SNS(SNS card_SNS) {
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

    public Music getCard_music() {
        return card_music;
    }

    public void setCard_music(Music card_music) {
        this.card_music = card_music;
    }

    public String getCard_movie() {
        return card_movie;
    }

    public void setCard_movie(String card_movie) {
        this.card_movie = card_movie;
    }
}
