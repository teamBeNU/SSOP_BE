package SSOP.ssop.dto.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpMemberDto {
    private String team_id;
    private String team_name;
    private String team_comment;
    private List<Long> user_id;
    private List<MemberResponse> members;

    // 기본 생성자
    public TeamSpMemberDto() {
        this.user_id = Collections.emptyList(); // 빈 배열로 초기화
    }

    // 모든 필드를 인자로 받는 생성자
    public TeamSpMemberDto(String team_id, String team_name, String team_comment, List<Long> user_id, List<MemberResponse> members) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.user_id = user_id;
        this.members = members;
    }

    // Getter 및 Setter
    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
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

    public List<Long> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<Long> user_id) {
        this.user_id = user_id;
    }

    public void setMembers(List<MemberResponse> members) {
        this.members = members;
    }
}
