package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.domain.card.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterMemberDto {
    private Long userId;
    private Long cardId;
    private String card_name;
    private String card_introduction;
    private String card_birth;
    private String card_template;
    private String profile_image_url;

    public FilterMemberDto(Member member) {
        this.userId = member.getTeamSpMember().getUser().getUserId();
        this.card_name = member.getCard_name();
        this.card_introduction = member.getCard_introduction();
        this.card_birth = member.getCard_birth();
        this.card_template = member.getCard_template();
        this.profile_image_url = member.getProfile_image_url();
    }
    public FilterMemberDto(Card card) {
        this.cardId = card.getCardId();
        this.card_name = card.getCard_name();
        this.card_introduction = card.getCard_introduction();
        this.card_birth = card.getCard_birth();
        this.card_template = card.getCard_template();
        this.profile_image_url = card.getProfile_image_url();
    }
}