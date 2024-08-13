package SSOP.ssop.dto;

import java.util.Collections;
import java.util.List;

public class TeamSpMemberDto {
    private String teamId;
    private List<Long> userIds;

    // 기본 생성자
    public TeamSpMemberDto() {
        this.userIds = Collections.emptyList(); // 빈 배열로 초기화
    }

    // 모든 필드를 인자로 받는 생성자
    public TeamSpMemberDto(String teamId, List<Long> userIds) {
        this.teamId = teamId;
        this.userIds = userIds;
    }

    // Getter 및 Setter
    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
