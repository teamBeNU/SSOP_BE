package SSOP.ssop.dto.response;

import SSOP.ssop.domain.Card;

import java.util.List;

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

    private int card_studentId;
    private String card_student_major;
    private String card_student_role;
    private String card_student_club;

    public CardResponse(Long id, String template, String background) {
        this.id = id;
        this.template = template;
        this.background = background;
    }

    public CardResponse(long id, Card card) {
        this.id = id;
        this.template = card.getTemplate();
        this.background = card.getBackground();
    }


    public CardResponse(Card card) {
        this.id = card.getId();
        this.template = card.getTemplate();
        this.background = card.getBackground();

        this.name = card.getName();
        this.introduction = card.getIntroduction();

        this.card_SNS = card.getCard_SNS();
        this.card_email = card.getCard_email();
        this.card_MBTI = card.getCard_MBTI();
        this.card_music = card.getCard_music();

        this.tel = card.getTel();
        this.birth = card.getBirth();
        this.school = card.getSchool();
        this.grade = card.getGrade();

        this.card_studentId = card.getCard_studentId();
        this.card_student_major = card.getCard_student_major();
        this.card_student_role = card.getCard_student_role();
        this.card_student_club = card.getCard_student_club();
    }

    public CardResponse(String message) throws IllegalArgumentException {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Card data is missing.");
        }
        this.name = message;
    }
}

