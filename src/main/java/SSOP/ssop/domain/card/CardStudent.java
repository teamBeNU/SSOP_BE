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

    public CardStudent() {
        super();
    }

    public CardStudent(
            String card_name,
            String card_introduction,
            String template,
            String card_cover,
            Avatar avatar,
            String profile_image_url,
            SNS card_SNS,
            String card_email,
            String card_MBTI,
            Music card_music,
            String card_movie,
            String card_tel,
            LocalDate card_birth,
            Boolean card_bSecrete,
            String card_school,
            String card_grade,
            String card_student_major,
            String card_student_club,
            String card_student_role
    ) { //String profile_image_url,
        super(card_name, card_introduction, template, card_cover, avatar, profile_image_url, card_SNS, card_email, card_MBTI, card_music, card_movie);

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
