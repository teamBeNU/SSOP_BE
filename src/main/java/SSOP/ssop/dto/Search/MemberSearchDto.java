package SSOP.ssop.dto.Search;

import SSOP.ssop.domain.TeamSp.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberSearchDto {
    private Long cardId;
    private String card_name;
    private String card_introduction;
    private String card_birth;
    private String card_template;
    private String card_cover;

    // 생성자
    public MemberSearchDto(Member member) {
        this.cardId = member.getCardId();
        this.card_name = member.getCard_name();
        this.card_introduction = member.getCard_introduction();
        this.card_birth = member.getCard_birth();
        this.card_template = member.getCard_template();
        this.card_cover = member.getCard_cover();
    }
}
