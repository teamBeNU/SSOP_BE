package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.*;

public class CardResponse {

    private long userId;
    private long card_id;
    private String card_template;
    private String card_cover;

    private CardEssential cardEssential;
    private CardOptional cardOptional;
    private TemplateEssential templateEssential;
    private TemplateOptional templateOptional;

    private String memo;
/*
    public CardResponse(Card card) {
        this.userId = card.getUser().getUserId();
        this.card_id = card.getCard_id();
        this.card_template = card.getCard_template();
        this.card_cover = card.getcard_cover();

        this.cardEssential = new CardEssential(card.getCard_name(), card.getCard_introduction());
        this.cardOptional = new CardOptional(card.getCard_SNS(), card.getCard_email(), card.getCard_MBTI(), card.getCard_music());

        this.templateEssential = new TemplateEssential(card.getCard_tel(), card.getCard_birth(), card.getCard_school(), card.getCard_grade());
        this.templateOptional = new TemplateOptional(card.getCard_studentId(), card.getCard_student_major(), card.getCard_student_role(), card.getCard_student_club());

        this.memo = card.getMemo();
    }*/

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public CardEssential getCardEssential() {
        return cardEssential;
    }

    public void setCardEssential(CardEssential cardEssential) {
        this.cardEssential = cardEssential;
    }

    public CardOptional getCardOptional() {
        return cardOptional;
    }

    public void setCardOptional(CardOptional cardOptional) {
        this.cardOptional = cardOptional;
    }

    public TemplateEssential getTemplateEssential() {
        return templateEssential;
    }

    public void setTemplateEssential(TemplateEssential templateEssential) {
        this.templateEssential = templateEssential;
    }

    public TemplateOptional getTemplateOptional() {
        return templateOptional;
    }

    public void setTemplateOptional(TemplateOptional templateOptional) {
        this.templateOptional = templateOptional;
    }

    public long getCard_id() {
        return card_id;
    }

    public void setCard_id(long card_id) {
        this.card_id = card_id;
    }

    public String getCard_template() {
        return card_template;
    }

    public void setCard_template(String card_template) {
        this.card_template = card_template;
    }

    public String getcard_cover() {
        return card_cover;
    }

    public void setcard_cover(String card_cover) {
        this.card_cover = card_cover;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}

