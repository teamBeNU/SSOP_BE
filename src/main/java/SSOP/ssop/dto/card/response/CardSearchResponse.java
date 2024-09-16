package SSOP.ssop.dto.card.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardSearchResponse {
    private Long cardId;
    private String card_name;
    private String card_introduce;
    private String card_birth;
    private String card_template;
    private String card_cover;

//    private String card_tel;
//    private String card_sns_insta;
//    private String card_sns_x;
//    private String card_email;
//    private String card_MBTI;
//    private String card_music;
//    private String card_movie;
//    private String card_hobby;
//    private String card_address;
//    private String memo;
}
