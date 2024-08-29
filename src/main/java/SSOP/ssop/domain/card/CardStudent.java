package SSOP.ssop.domain.card;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("student")
public class CardStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;      // card의 id

    // 필수
    @Column(name = "card_student_school")
    private String card_student_school;     // 학교

    @Column(name = "card_student_grade")
    private String card_student_grade;      // 학년(초중고-1~6학년, 대학(원)-1~4학년+추가학기)

    @Column(name = "card_student_major")
    private String card_student_major;  // 전공(대학(원)생만 필수)

    // 선택
    @Column(name = "card_student_id")
    private String card_student_id;         // 학번

    @Column(name = "card_student_club")
    private String card_student_club;       // 동아리

    @Column(name = "card_student_role")
    private String card_student_role;       // 역할

    @Column(name = "card_student_status")
    private String card_student_status;  // 재학상태(재학 중, 휴학 중, 졸업 예정)

    public CardStudent() {
        super();
    }

    public CardStudent(
            String card_student_school,
            String card_student_grade,
            String card_student_major,
            String card_student_id,
            String card_student_club,
            String card_student_role,
            String card_student_status
    ) {

//        if (card_student_school == null || card_student_school.isBlank() ||
//                card_student_grade == null || card_student_grade.isBlank()) {
//            throw new IllegalArgumentException();
//        }

        this.card_student_school = card_student_school;
        this.card_student_grade = card_student_grade;
        this.card_student_major = card_student_major;
        this.card_student_id = card_student_id;
        this.card_student_club = card_student_club;
        this.card_student_role = card_student_role;
        this.card_student_status = card_student_status;
    }

    public Card getCard() {
        return card;
    }

    public String getCard_student_school() {
        return card_student_school;
    }

    public String getCard_student_grade() {
        return card_student_grade;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public String getCard_student_id() {
        return card_student_id;
    }

    public String getCard_student_club() {
        return card_student_club;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public String getCard_student_status() {
        return card_student_status;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void setCard_student_school(String card_student_school) {
        this.card_student_school = card_student_school;
    }

    public void setCard_student_grade(String card_student_grade) {
        this.card_student_grade = card_student_grade;
    }

    public void setCard_student_major(String card_student_major) {
        this.card_student_major = card_student_major;
    }

    public void setCard_student_id(String card_student_id) {
        this.card_student_id = card_student_id;
    }

    public void setCard_student_club(String card_student_club) {
        this.card_student_club = card_student_club;
    }

    public void setCard_student_role(String card_student_role) {
        this.card_student_role = card_student_role;
    }

    public void setCard_student_status(String card_student_status) {
        this.card_student_status = card_student_status;
    }
}
