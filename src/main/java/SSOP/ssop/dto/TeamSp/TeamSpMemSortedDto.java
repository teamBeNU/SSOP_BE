package SSOP.ssop.dto.TeamSp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamSpMemSortedDto {
    @JsonIgnore
    private Long userId;
    private Long cardId;
    private String card_name;
    private String card_birth;
    private String card_status;
//    @JsonIgnore
    private LocalDateTime createdAt;
}
