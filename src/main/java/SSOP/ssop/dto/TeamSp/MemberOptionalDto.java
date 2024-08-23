package SSOP.ssop.dto.TeamSp;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberOptionalDto {

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

    public MemberOptionalDto(LocalDate card_birth, String card_MBTI, String card_tel, String card_email, String card_insta, String card_x, String card_hobby, String card_music, String card_movie, String card_address, String card_free_A1, String card_free_A2, String card_free_A3, String card_free_A4, String card_free_A5) {
        this.card_birth = card_birth;
        this.card_MBTI = card_MBTI;
        this.card_tel = card_tel;
        this.card_email = card_email;
        this.card_insta = card_insta;
        this.card_x = card_x;
        this.card_hobby = card_hobby;
        this.card_music = card_music;
        this.card_movie = card_movie;
        this.card_address = card_address;
        this.card_free_A1 = card_free_A1;
        this.card_free_A2 = card_free_A2;
        this.card_free_A3 = card_free_A3;
        this.card_free_A4 = card_free_A4;
        this.card_free_A5 = card_free_A5;
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
