package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardOptionalCreateRequest {

    private String card_birth;      // 생년월일
    private String card_tel;    // 연락처
    private String card_sns_insta;  // sns: insta
    private String card_sns_x;      // sns: x
    private String card_email;      // 이메일
    private String card_MBTI;       // mbti
    private String card_music;      // 음악
    private String card_movie;      // 영화
    private String card_hobby;      // 취미
    private String card_address;        // 거주지
}
