package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;

public class FilterDto {
    private Long card_id;
    private String card_mbti;
    private String card_student_major;
    private String card_student_role;

    public FilterDto(Member member) {
        this.card_id = member.getCardId();
        this.card_mbti = member.getCard_MBTI();
        this.card_student_major = member.getCard_student_major();
        this.card_student_role = member.getCard_student_role();
    }

    public Long getCard_id() {
        return card_id;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public String getCard_mbti() {
        return card_mbti;
    }

    public void setCard_mbti(String card_mbti) {
        this.card_mbti = card_mbti;
    }

    public String getCard_student_role() {
        return card_student_role;
    }

    public void setCard_student_role(String card_student_role) {
        this.card_student_role = card_student_role;
    }

    public String getCard_student_major() {
        return card_student_major;
    }

    public void setCard_student_major(String card_student_major) {
        this.card_student_major = card_student_major;
    }
}
