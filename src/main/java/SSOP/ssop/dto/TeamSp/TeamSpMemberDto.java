package SSOP.ssop.dto.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpMemberDto {
    private Long teamId;
    private String team_name;
    private String team_comment;
    private List<Long> userIds;
    private List<Long> cardIds;
    private List<MemberResponse> members;

    // 기본 생성자
    public TeamSpMemberDto() {
        this.userIds = Collections.emptyList(); // 빈 리스트로 초기화
        this.cardIds = Collections.emptyList(); // 빈 리스트로 초기화
        this.members = Collections.emptyList();  // 빈 리스트로 초기화
    }

    // 모든 필드를 인자로 받는 생성자
    public TeamSpMemberDto(Long teamId, String team_name, String team_comment, List<Long> userIds, List<Long> cardIds, List<MemberResponse> members) {
        this.teamId = teamId;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.userIds = userIds;
        this.cardIds = cardIds;
        this.members = members;
    }

    // Getter 및 Setter
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeam_name() {
        return team_name;
    }

    public List<MemberResponse> getMembers() {
        return members;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_comment() {
        return team_comment;
    }

    public void setTeam_comment(String team_comment) {
        this.team_comment = team_comment;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUser_id(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getCardIds() {
        return cardIds;
    }

    public void setCard_id(List<Long> cardIds) {
        this.cardIds = cardIds;
    }

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }
}
