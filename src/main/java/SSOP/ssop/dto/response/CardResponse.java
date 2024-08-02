package SSOP.ssop.dto.response;

import SSOP.ssop.domain.Card;

public class CardResponse {
    private long id;
    private String template;
    private String background;

    private String name;
    private String introduction;

    private String card_SNS;
    private String card_email;
    private String card_MBTI;
    private String card_music;

    private String tel;
    private String birth;
    private String school;
    private String grade;

    private Integer card_studentId;
    private String card_student_major;
    private String card_student_role;
    private String card_student_club;

    public CardResponse(Card card) {
        this.id = card.getCard_id();
        this.template = card.getCard_template();
        this.background = card.getCard_background();

        this.name = card.getCard_name();
        this.introduction = card.getCard_introduction();

        this.card_SNS = card.getCard_SNS();
        this.card_email = card.getCard_email();
        this.card_MBTI = card.getCard_MBTI();
        this.card_music = card.getCard_music();

        this.tel = card.getCard_tel();
        this.birth = card.getCard_birth();
        this.school = card.getCard_school();
        this.grade = String.valueOf(card.getCard_grade());

        this.card_studentId = card.getCard_studentId();
        this.card_student_major = card.getCard_student_major();
        this.card_student_role = card.getCard_student_role();
        this.card_student_club = card.getCard_student_club();
    }

}

