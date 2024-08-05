package SSOP.ssop.domain.card;

import SSOP.ssop.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StructedCard {
    private CardEssential card_essential;
    private CardOptional card_optional;
    private TemplateEssential template_essential;
    private TemplateOptional template_optional;
    private User user;

    private long card_id;
    private String card_template;
    private String card_background;


    public StructedCard(Card card) {
        this.user = new User(user.getUserId());
        this.card_id = card.getCard_id();
        this.card_template = card.getCard_template();
        this.card_background = card.getCard_background();
        this.card_essential = new CardEssential(
                card.getCard_name(),
                card.getCard_introduction()
        );
        this.card_optional = new CardOptional(
                card.getCard_SNS(),
                card.getCard_email(),
                card.getCard_MBTI(),
                card.getCard_music()
        );
        this.template_essential = new TemplateEssential(
                card.getCard_tel(),
                card.getCard_birth(),
                card.getCard_school(),
                card.getCard_grade()
        );
        this.template_optional = new TemplateOptional(
                card.getCard_studentId(),
                card.getCard_student_major(),
                card.getCard_student_role(),
                card.getCard_student_club()
        );
    }

    public CardEssential getCard_essential() {
        return card_essential;
    }

    public void setCard_essential(CardEssential card_essential) {
        this.card_essential = card_essential;
    }

    public CardOptional getCard_optional() {
        return card_optional;
    }

    public void setCard_optional(CardOptional card_optional) {
        this.card_optional = card_optional;
    }

    public TemplateEssential getTemplate_essential() {
        return template_essential;
    }

    public void setTemplate_essential(TemplateEssential template_essential) {
        this.template_essential = template_essential;
    }

    public TemplateOptional getTemplate_optional() {
        return template_optional;
    }

    public void setTemplate_optional(TemplateOptional template_optional) {
        this.template_optional = template_optional;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getCard_background() {
        return card_background;
    }

    public void setCard_background(String card_background) {
        this.card_background = card_background;
    }
}
