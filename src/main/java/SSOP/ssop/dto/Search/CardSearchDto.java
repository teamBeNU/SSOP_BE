package SSOP.ssop.dto.Search;

import SSOP.ssop.domain.card.Card;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardSearchDto {
    private Long cardId;
    private String card_name;
    private String card_introduction;
    private String card_birth;
    private String card_template;
    private String profile_image_url;
}