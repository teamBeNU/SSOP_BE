package SSOP.ssop.domain.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardOptional {
    private String card_birth;      // 생년월일
    private String card_tel;        // 연락처
    private String card_sns_insta;  // sns: insta
    private String card_sns_x;      // sns: x
    private String card_email;      // 이메일
    private String card_MBTI;       // mbti
    private String card_music;      // 음악
    private String card_movie;      // 영화
    private String card_hobby;      // 취미
    private String card_address;    // 거주지

    public CardOptional(String cardBirth, String cardTel, String cardSnsInsta, String cardSnsX, String cardEmail, String cardMbti, String cardMusic, String cardMusic1, String cardMovie, String cardHobby, String cardAddress) {
        this.card_birth = cardBirth;
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
}
