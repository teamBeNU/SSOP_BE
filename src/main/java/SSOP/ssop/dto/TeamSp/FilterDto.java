package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class FilterDto {
    @JsonIgnore
    private Long cardId;
    private List<String> card_mbti;
    private List<String> card_major;
    private List<String> card_role;
    private List<String> card_template;

    public FilterDto(Member member) {
        this.cardId = member.getCardId();
        this.card_mbti = Collections.singletonList(member.getCard_MBTI());
        this.card_major = Collections.singletonList(member.getCard_student_major());
        this.card_role = Collections.singletonList(member.getCard_student_role());
        this.card_template = Collections.singletonList(member.getCard_template());
    }
}
