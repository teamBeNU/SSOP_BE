package SSOP.ssop.dto.TeamSp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeamSpByUserDto {
    private Long teamId;
    private String team_name;
    private String team_comment;
    private Long cardId;
    private List<MemberResponse> members;
}
