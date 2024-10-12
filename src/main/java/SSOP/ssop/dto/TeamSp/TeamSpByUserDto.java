package SSOP.ssop.dto.TeamSp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamSpByUserDto {
    private Long teamId;
    private Long hostId;
    private String team_name;
    private String team_comment;
    private int memberCount; // 참여 인원 수
    private Long cardId;
    private List<MemberResponse> members;
}