package SSOP.ssop.dto.TeamSp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class TeamSpMemberDto {
    private Long teamId;
    private Long hostId;
    private String team_name;
    private String team_comment;
    private FilterDto filter;
    private int memberCount; // 참여 인원 수
    private List<Long> userIds;
    private List<Long> cardIds;
    private List<MemberResponse> members;

    // 기본 생성자
    public TeamSpMemberDto() {
        this.userIds = Collections.emptyList(); // 빈 리스트로 초기화
        this.cardIds = Collections.emptyList(); // 빈 리스트로 초기화
        this.members = Collections.emptyList();  // 빈 리스트로 초기화
        this.memberCount = 0; // 기본값 0으로 초기화
    }

    public TeamSpMemberDto(Long teamId, Long hostId, String team_name, String team_comment, FilterDto filter, List<Long> userIds, List<Long> cardIds, List<MemberResponse> members) {
        this.teamId = teamId;
        this.hostId = hostId;
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.filter = filter;
        this.userIds = userIds != null ? userIds : Collections.emptyList();
        this.memberCount = (int) this.cardIds.stream()
                .filter(Objects::nonNull) // null 값 제외
                .count();
        this.cardIds = cardIds != null ? cardIds : Collections.emptyList();
        this.members = members != null ? members : Collections.emptyList();
    }

}