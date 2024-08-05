package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.*;

public class CardResponse {

    private long card_id;
    private String card_template;
    private String card_background;

    private CardEssential cardEssential;
    private CardOptional cardOptional;
    private TemplateEssential templateEssential;
    private TemplateOptional templateOptional;

//    public CardResponse(String card_student_club, String card_student_role, String card_student_major, Integer card_studentId, int card_grade, String card_school, String card_birth, String card_tel, String card_music, String card_MBTI, String card_email, String card_SNS, String card_introduction, String card_name, String card_background, String card_template, long card_id) {
//        this.card_student_club = card_student_club;
//        this.card_student_role = card_student_role;
//        this.card_student_major = card_student_major;
//        this.card_studentId = card_studentId;
//        this.card_grade = card_grade;
//        this.card_school = card_school;
//        this.card_birth = card_birth;
//        this.card_tel = card_tel;
//        this.card_music = card_music;
//        this.card_MBTI = card_MBTI;
//        this.card_email = card_email;
//        this.card_SNS = card_SNS;
//        this.card_introduction = card_introduction;
//        this.card_name = card_name;
//        this.card_background = card_background;
//        this.card_template = card_template;
//        this.card_id = card_id;
//    }

    public CardResponse(Card card) {
        this.card_id = card.getCard_id();
        this.card_template = card.getCard_template();
        this.card_background = card.getCard_background();

        this.cardEssential = new CardEssential(card.getCard_name(), card.getCard_introduction());
        this.cardOptional = new CardOptional(card.getCard_SNS(), card.getCard_email(), card.getCard_MBTI(), card.getCard_music());

        this.templateEssential = new TemplateEssential(card.getCard_tel(), card.getCard_birth(), card.getCard_school(), card.getCard_grade());
        this. templateOptional = new TemplateOptional(card.getCard_studentId(), card.getCard_student_major(), card.getCard_student_role(), card.getCard_student_club());
    }

    public CardEssential getCardEssential() {
        return cardEssential;
    }

    public void setCardEssential(CardEssential cardEssential) {
        this.cardEssential = cardEssential;
    }

    public CardOptional getCardOptional() {
        return cardOptional;
    }

    public void setCardOptional(CardOptional cardOptional) {
        this.cardOptional = cardOptional;
    }

    public TemplateEssential getTemplateEssential() {
        return templateEssential;
    }

    public void setTemplateEssential(TemplateEssential templateEssential) {
        this.templateEssential = templateEssential;
    }

    public TemplateOptional getTemplateOptional() {
        return templateOptional;
    }

    public void setTemplateOptional(TemplateOptional templateOptional) {
        this.templateOptional = templateOptional;
    }

    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
    }

    public String getCard_template() {
        return card_template;
    }

    public void setCard_template(String card_template) {
        this.card_template = card_template;
    }

    public String getCard_background() {
        return card_background;
    }

    public void setCard_background(String card_background) {
        this.card_background = card_background;
    }

//    public String getCard_name() {
//        return card_name;
//    }
//
//    public void setCard_name(String card_name) {
//        this.card_name = card_name;
//    }
//
//    public String getCard_introduction() {
//        return card_introduction;
//    }
//
//    public void setCard_introduction(String card_introduction) {
//        this.card_introduction = card_introduction;
//    }
//
//    public String getCard_SNS() {
//        return card_SNS;
//    }
//
//    public void setCard_SNS(String card_SNS) {
//        this.card_SNS = card_SNS;
//    }
//
//    public String getCard_email() {
//        return card_email;
//    }
//
//    public void setCard_email(String card_email) {
//        this.card_email = card_email;
//    }
//
//    public String getCard_MBTI() {
//        return card_MBTI;
//    }
//
//    public void setCard_MBTI(String card_MBTI) {
//        this.card_MBTI = card_MBTI;
//    }
//
//    public String getCard_music() {
//        return card_music;
//    }
//
//    public void setCard_music(String card_music) {
//        this.card_music = card_music;
//    }
//
//    public String getCard_tel() {
//        return card_tel;
//    }
//
//    public void setCard_tel(String card_tel) {
//        this.card_tel = card_tel;
//    }
//
//    public String getCard_birth() {
//        return card_birth;
//    }
//
//    public void setCard_birth(String card_birth) {
//        this.card_birth = card_birth;
//    }
//
//    public String getCard_school() {
//        return card_school;
//    }
//
//    public void setCard_school(String card_school) {
//        this.card_school = card_school;
//    }
//
//    public int getCard_grade() {
//        return card_grade;
//    }
//
//    public void setCard_grade(int card_grade) {
//        this.card_grade = card_grade;
//    }
//
//    public Integer getCard_studentId() {
//        return card_studentId;
//    }
//
//    public void setCard_studentId(Integer card_studentId) {
//        this.card_studentId = card_studentId;
//    }
//
//    public String getCard_student_major() {
//        return card_student_major;
//    }
//
//    public void setCard_student_major(String card_student_major) {
//        this.card_student_major = card_student_major;
//    }
//
//    public String getCard_student_role() {
//        return card_student_role;
//    }
//
//    public void setCard_student_role(String card_student_role) {
//        this.card_student_role = card_student_role;
//    }
//
//    public String getCard_student_club() {
//        return card_student_club;
//    }
//
//    public void setCard_student_club(String card_student_club) {
//        this.card_student_club = card_student_club;
//    }
}

