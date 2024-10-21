package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterMemberDto {
    private Long teamId;
    private Long cardId;
    private String card_name;
    private String card_comment;
    private String card_birth;
    private String template;
    private String profile_image_url;
//    private String avatar;

    public FilterMemberDto(Member member) {
        this.teamId = member.getTeamSpMember().getTeamSp().getTeamId();
        this.cardId = member.getCardId();
        this.card_name = member.getCard_name();
        this.card_comment = member.getCard_introduction();
        this.card_birth = member.getCard_birth();
        this.template = member.getCard_template();
        this.profile_image_url = member.getProfile_image_url();
//        this.avatar = member.
    }
}