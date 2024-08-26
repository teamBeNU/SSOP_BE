package SSOP.ssop.dto.card.request;

import SSOP.ssop.domain.card.Avatar;

public class CardEssentialCreateRequest {

    private String card_name;       // 이름
    private String card_introduction;       // 한줄소개
    private String card_template;       // 템플릿 종류(student, worker, fan, free)
    private String card_cover;     // 카드 커버(avatar, picture)

    public String getCard_name() {
        return card_name;
    }

    public String getCard_introduction() {
        return card_introduction;
    }

    public String getCard_template() {
        return card_template;
    }

    public String getCard_cover() {
        return card_cover;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public void setCard_introduction(String card_introduction) {
        this.card_introduction = card_introduction;
    }

    public void setCard_template(String card_template) {
        this.card_template = card_template;
    }

    public void setCard_cover(String card_cover) {
        this.card_cover = card_cover;
    }
}
