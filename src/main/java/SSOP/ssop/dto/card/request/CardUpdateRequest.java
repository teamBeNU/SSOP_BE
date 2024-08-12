package SSOP.ssop.dto.card.request;

public class CardUpdateRequest {
    private long card_id;
    private String card_template;
    private String card_cover;

    private String card_name;
    private String card_introduction;

    private String card_SNS;
    private String card_email;
    private String card_MBTI;
    private String card_music;

    private String card_tel;
    private String card_birth;
    private String card_school;
    private Integer card_grade;

    private Integer card_studentId;
    private String card_student_major;
    private String card_student_role;
    private String card_student_club;


    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
    }

    public String getCard_template() {
        return card_template;
    }

    public String getcard_cover() {
        return card_cover;
    }

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
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

    public String getCard_tel() {
        return card_tel;
    }

    public String getCard_birth() {
        return card_birth;
    }

    public String getCard_school() {
        return card_school;
    }

    public Integer getCard_grade() {
        return card_grade;
    }

    public Integer getCard_studentId() {
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
