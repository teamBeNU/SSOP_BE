package SSOP.ssop.domain.card;

public class TemplateEssential {
    private String card_tel;
    private String card_birth;
    private String card_school;
    private Integer card_grade;

    public TemplateEssential(String card_tel, String card_birth, String card_school, Integer card_grade) {
        this.card_tel = card_tel;
        this.card_birth = card_birth;
        this.card_school = card_school;
        this.card_grade = card_grade;
    }

    public String getCard_tel() {
        return card_tel;
    }

    public void setCard_tel(String card_tel) {
        this.card_tel = card_tel;
    }

    public String getCard_birth() {
        return card_birth;
    }

    public void setCard_birth(String card_birth) {
        this.card_birth = card_birth;
    }

    public String getCard_school() {
        return card_school;
    }

    public void setCard_school(String card_school) {
        this.card_school = card_school;
    }

    public Integer getCard_grade() {
        return card_grade;
    }

    public void setCard_grade(Integer card_grade) {
        this.card_grade = card_grade;
    }
}
