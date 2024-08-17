package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.CardStudent;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardStudentResponse extends CardStudent {

    private String card_tel;
    private LocalDate card_birth;
    private String card_school;
    private String card_grade;
    private String card_student_major;
    private String card_student_club;
    private String card_student_role;

    public CardStudentResponse(
            String card_tel,
            LocalDate card_birth,
            String card_school,
            String card_grade,
            String card_student_major,
            String card_student_club,
            String card_student_role) {
        this.card_tel = card_tel;
        this.card_birth = card_birth;
        this.card_school = card_school;
        this.card_grade = card_grade;
        this.card_student_major = card_student_major;
        this.card_student_club = card_student_club;
        this.card_student_role = card_student_role;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public void setCard_birth(LocalDate card_birth) {
        this.card_birth = card_birth;
    }

    public String getCard_school() {
        return card_school;
    }

    public void setCard_school(String card_school) {
        this.card_school = card_school;
    }

    public String getCard_grade() {
        return card_grade;
    }

    public void setCard_grade(String card_grade) {
        this.card_grade = card_grade;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public void setCard_student_major(String card_student_major) {
        this.card_student_major = card_student_major;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public void setCard_student_club(String card_student_club) {
        this.card_student_club = card_student_club;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public void setCard_student_role(String card_student_role) {
        this.card_student_role = card_student_role;
    }
}
