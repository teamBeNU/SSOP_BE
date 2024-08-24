package SSOP.ssop.dto.card.request;

import java.time.LocalDate;

public class CardStudentCreateRequest {

    private String card_tel;
    private LocalDate card_birth;
    private Boolean card_bSecrete;
    private String card_school;
    private String card_grade;
    private String card_student_major;
    private String card_student_club;
    private String card_student_role;

    public String getCard_tel() {
        return card_tel;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public Boolean getCard_bSecrete() {
        return card_bSecrete;
    }

    public String getCard_school() {
        return card_school;
    }

    public String getCard_grade() {
        return card_grade;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public String getCard_student_role() {
        return card_student_role;
    }
}
