package SSOP.ssop.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "template_id")
    private Template template;

    @Column(nullable = false, name = "card_background")
    private String background;


    // card_essential
    @Column(nullable = false, name = "card_name")
    private String name;

    @Column(nullable = false, name = "card_introduction")
    private String introduction;

    // card_optional
    private String card_SNS;
    private String card_email;
    private String card_MBTI;
    private String card_music;

    // template_essential
    @Column(nullable = false, name = "card_tel")
    private String tel;

    @Column(nullable = false, name = "card_birth")
    private String birth;

    @Column(nullable = false, name = "card_school")
    private String school;

    @Column(nullable = false, name = "card_grade")
    private String grade;


    // template_optional
    private int card_studentId;
    private String card_student_major;
    private String card_student_role;
    private String card_student_club;

    protected Card() {}

    // getter
    public Long getId() {
        return id;
    }

    public String getTemplate() {
        return template.toString();
    }

    public String getBackground() {
        return background;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getCard_SNS() {
        return card_SNS;
    }

    public String getCard_email() {
        return card_email;
    }

    public String getCard_MBTI() {
        return card_MBTI;
    }

    public String getCard_music() {
        return card_music;
    }

    public String getTel() {
        return tel;
    }

    public String getBirth() {
        return birth;
    }

    public String getSchool() {
        return school;
    }

    public String getGrade() {
        return grade;
    }

    public int getCard_studentId() {
        return card_studentId;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public String getCard_student_club() {
        return card_student_club;
    }
}
