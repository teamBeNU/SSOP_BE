package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDto {
    private Long cardId;
    private String card_mbti;
    private String card_student_major;
    private String card_student_role;

    public FilterDto(Member member) {
        this.cardId = member.getCardId();
        this.card_mbti = member.getCard_MBTI();
        this.card_student_major = member.getCard_student_major();
        this.card_student_role = member.getCard_student_role();
    }
}
