package SSOP.ssop.domain.card;

public class CardOptional {
    private String card_birth;      // 생년월일
    private Boolean card_bSecrete;  // 생년월일 비밀
    private String card_tel;        // 연락처
    private String card_sns_insta;  // sns: insta
    private String card_sns_x;      // sns: x
    private String card_email;      // 이메일
    private String card_MBTI;       // mbti
    private String card_music;      // 음악
    private String card_movie;      // 영화
    private String card_hobby;      // 취미
    private String card_address;    // 거주지

    public CardOptional(String cardBirth, Boolean cardBSecrete, String cardTel, String cardSnsInsta, String cardSnsX, String cardEmail, String cardMbti, String cardMusic, String cardMusic1, String cardMovie, String cardHobby, String cardAddress) {
        this.card_birth = cardBirth;
        this.card_bSecrete = cardBSecrete;
        this.card_tel = cardTel;
        this.card_sns_insta = cardSnsInsta;
        this.card_sns_x = cardSnsX;
        this.card_email = cardEmail;
        this.card_MBTI = cardMbti;
        this.card_music = cardMusic;
        this.card_movie = cardMovie;
        this.card_hobby = cardHobby;
        this.card_address = cardAddress;
    }

    public String getCard_birth() {
        return card_birth;
    }

    public void setCard_birth(String card_birth) {
        this.card_birth = card_birth;
    }

    public Boolean getCard_bSecrete() {
        return card_bSecrete;
    }

    public void setCard_bSecrete(Boolean card_bSecrete) {
        this.card_bSecrete = card_bSecrete;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }

    public String getCard_sns_insta() {
        return card_sns_insta;
    }

    public void setCard_sns_insta(String card_sns_insta) {
        this.card_sns_insta = card_sns_insta;
    }

    public String getCard_sns_x() {
        return card_sns_x;
    }

    public void setCard_sns_x(String card_sns_x) {
        this.card_sns_x = card_sns_x;
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

    public String getCard_movie() {
        return card_movie;
    }

    public void setCard_movie(String card_movie) {
        this.card_movie = card_movie;
    }

    public String getCard_hobby() {
        return card_hobby;
    }

    public void setCard_hobby(String card_hobby) {
        this.card_hobby = card_hobby;
    }

    public String getCard_address() {
        return card_address;
    }

    public void setCard_address(String card_address) {
        this.card_address = card_address;
    }
}
