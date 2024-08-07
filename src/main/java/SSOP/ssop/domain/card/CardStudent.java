package SSOP.ssop.domain.card;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("student")
public class CardStudent extends Card {

    // 필수
    @Column(nullable = false)
    private String card_tel;

    @Column(nullable = false)
    private LocalDate card_birth;

    @Column(nullable = false)
    private Boolean card_bSecrete;

    @Column(nullable = false)
    private String card_school;

    @Column(nullable = false)
    private String card_grade;

    // 선택
    private String card_student_major;
    private String card_student_club;
    private String card_student_role;

    public CardStudent() {}

    public CardStudent(String card_tel, LocalDate card_birth, Boolean card_bSecrete, String card_school, String card_grade, String card_student_major, String card_student_club, String card_student_role) {
        if (card_tel == null || card_tel.isBlank() ||
            card_birth == null || card_bSecrete == null ||
            card_school == null || card_school.isBlank() ||
            card_grade == null || card_grade.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.card_tel = card_tel;
        this.card_birth = card_birth;
        this.card_bSecrete = card_bSecrete;
        this.card_school = card_school;
        this.card_grade = card_grade;
        this.card_student_major = card_student_major;
        this.card_student_club = card_student_club;
        this.card_student_role = card_student_role;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public LocalDate getCard_birth() {
        return card_birth;
    }

    public boolean isCard_bSecrete() {
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

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }

    public void setCard_birth(LocalDate card_birth) {
        this.card_birth = card_birth;
    }

    public void setCard_bSecrete(Boolean card_bSecrete) {
        this.card_bSecrete = card_bSecrete;
    }

    public void setCard_school(String card_school) {
        this.card_school = card_school;
    }

    public void setCard_grade(String card_grade) {
        this.card_grade = card_grade;
    }

    public void setCard_student_major(String card_student_major) {
        this.card_student_major = card_student_major;
    }

    public void setCard_student_club(String card_student_club) {
        this.card_student_club = card_student_club;
    }

    public void setCard_student_role(String card_student_role) {
        this.card_student_role = card_student_role;
    }
}
