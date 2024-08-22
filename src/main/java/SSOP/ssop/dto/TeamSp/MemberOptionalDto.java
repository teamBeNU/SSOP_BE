package SSOP.ssop.dto.TeamSp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberOptionalDto {

    private String card_age;     // 나이

    private LocalDate card_birth;  // 생년월일

    private String card_MBTI;     // mbti

    private String card_tel;     // 연락처

    private String card_email;     // 이메일

    private String card_insta;     // 인스타

    private String card_x;     // x(트위터)

    private String card_hobby;     // 취미

    private String card_music;     // 인생음악

    private String card_movie;     // 인생영화

    private String card_address;     // 거주지

    private String card_free_A1;
    private String card_free_A2;
    private String card_free_A3;
    private String card_free_A4;
    private String card_free_A5;

    public String getCard_age() {
        return card_age;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_insta() {
        return card_insta;
    }

    public String getCard_x() {
        return card_x;
    }

    public String getCard_hobby() {
        return card_hobby;
    }

    public String getCard_music() {
        return card_music;
    }

    public String getCard_movie() {
        return card_movie;
    }

    public String getCard_address() {
        return card_address;
    }

    public String getCard_free_A1() {
        return card_free_A1;
    }

    public String getCard_free_A2() {
        return card_free_A2;
    }

    public String getCard_free_A3() {
        return card_free_A3;
    }

    public String getCard_free_A4() {
        return card_free_A4;
    }

    public String getCard_free_A5() {
        return card_free_A5;
    }
}
