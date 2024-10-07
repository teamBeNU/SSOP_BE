package SSOP.ssop.dto.TeamSp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeamSpMemberDto {
    private Long teamId;
    private Long hostId;
    private String team_name;
    private String team_comment;
    private FilterDto filter;
    private List<Long> userIds;
    private List<Long> cardIds;
    private List<MemberResponse> members;

    // 기본 생성자
    public TeamSpMemberDto() {
        this.userIds = Collections.emptyList(); // 빈 리스트로 초기화
        this.cardIds = Collections.emptyList(); // 빈 리스트로 초기화
        this.members = Collections.emptyList();  // 빈 리스트로 초기화
    }
}
