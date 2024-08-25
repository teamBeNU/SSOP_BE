package SSOP.ssop.dto.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpMemberDto {
    private Long team_id;
    private String team_name;
    private String team_comment;
    private List<Long> user_ids;
    private List<Long> card_ids;
    private List<MemberResponse> members;

    // 기본 생성자
    public TeamSpMemberDto() {
        this.user_ids = Collections.emptyList(); // 빈 리스트로 초기화
        this.card_ids = Collections.emptyList(); // 빈 리스트로 초기화
        this.members = Collections.emptyList();  // 빈 리스트로 초기화
    }

    // 모든 필드를 인자로 받는 생성자
    public TeamSpMemberDto(Long team_id, String team_name, String team_comment, List<Long> user_ids, List<Long> card_ids, List<MemberResponse> members) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.user_ids = user_ids;
        this.card_ids = card_ids;
        this.members = members;
    }

    // Getter 및 Setter
    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
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

    public List<Long> getUser_ids() {
        return user_ids;
    }

    public void setUser_id(List<Long> user_ids) {
        this.user_ids = user_ids;
    }

    public List<Long> getCard_ids() {
        return card_ids;
    }

    public void setCard_id(List<Long> card_ids) {
        this.card_ids = card_ids;
    }

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }
}
