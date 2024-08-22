package SSOP.ssop.dto.card.TeamSp;

import java.util.Collections;
import java.util.List;

public class TeamSpMemberDto {
    private String teamId;
    private String teamName;
    private List<Long> userIds;


    // 기본 생성자
    public TeamSpMemberDto() {
        this.userIds = Collections.emptyList(); // 빈 배열로 초기화
    }

    // 모든 필드를 인자로 받는 생성자
    public TeamSpMemberDto(String teamId, String teamName, List<Long> userIds) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.userIds = userIds;
    }

    // Getter 및 Setter
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
