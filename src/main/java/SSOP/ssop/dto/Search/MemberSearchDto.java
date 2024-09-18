package SSOP.ssop.dto.Search;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberSearchDto {
    private Long cardId;
    private String card_name;
    private String card_introduction;
    private String card_birth;
    private String card_template;
    private String card_cover;

    // 생성자
    public MemberSearchDto(Long cardId, String card_name, String card_introduction, String card_birth, String card_template, String card_cover) {
        this.cardId = cardId;
        this.card_name = card_name;
        this.card_introduction = card_introduction;
        this.card_birth = card_birth;
        this.card_template = card_template;
        this.card_cover = card_cover;
    }

    // Getter, Setter
    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }

    public String getCard_name() { return card_name; }
    public void setCard_name(String card_name) { this.card_name = card_name; }

    public String getCard_introduction() { return card_introduction; }
    public void setCard_introduction(String card_introduction) { this.card_introduction = card_introduction; }

    public String getCard_birth() { return card_birth; }
    public void setCard_birth(String card_birth) { this.card_birth = card_birth; }

    public String getCard_template() { return card_template; }
    public void setCard_template(String card_template) { this.card_template = card_template;}

    public String getCard_cover() { return card_cover; }
    public void setCard_cover(String card_cover) { this.card_cover = card_cover; }
}
