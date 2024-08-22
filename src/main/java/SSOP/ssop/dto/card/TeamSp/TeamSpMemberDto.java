package SSOP.ssop.dto.card.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpMemberDto {
    private String team_id;
    private String team_name;
    private String team_comment;
    private List<Long> userIds;


    // 기본 생성자
    public TeamSpMemberDto() {
        this.userIds = Collections.emptyList(); // 빈 배열로 초기화
    }

    // 모든 필드를 인자로 받는 생성자
    public TeamSpMemberDto(String team_id, String team_name, String team_comment, List<Long> userIds) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.userIds = userIds;
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

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
