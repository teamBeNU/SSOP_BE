package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.*;

public class SavedCardResponse {

    private long card_id;
    private String card_cover;
    private CardEssential cardEssential;
    private CardOptional cardOptional;
    private TemplateEssential templateEssential;
    private TemplateOptional templateOptional;
/*
    public SavedCardResponse(Card card) {
        this.card_id = card.getUser().getUserId();
        this.card_cover = card.getcard_cover();
        this.cardEssential = new CardEssential(card.getCard_name(), card.getCard_introduction());
        this.cardOptional = new CardOptional(card.getCard_SNS(), card.getCard_email(), card.getCard_MBTI(), card.getCard_music());
        this.templateEssential = new TemplateEssential(card.getCard_tel(), card.getCard_birth(), card.getCard_school(), card.getCard_grade());
        this.templateOptional = new TemplateOptional(card.getCard_studentId(), card.getCard_student_major(), card.getCard_student_role(), card.getCard_student_club());
    }*/

    public long getCard_id() {
        return card_id;
    }

    public String getcard_cover() {
        return card_cover;
    }

    public CardEssential getCardEssential() {
        return cardEssential;
    }

    public CardOptional getCardOptional() {
        return cardOptional;
    }

    public TemplateEssential getTemplateEssential() {
        return templateEssential;
    }

    public TemplateOptional getTemplateOptional() {
        return templateOptional;
    }
}
