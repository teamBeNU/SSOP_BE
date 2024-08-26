package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponse {

    private Long card_id;
    private Long user_id;

    private String card_template;  // student or worker
    private String card_cover;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Avatar avatar;

    private CardEssential cardEssential;
    private CardOptional cardOptional;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardStudent student;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardWorker worker;

    public CardResponse(Card card) {
        this.user_id = card.getUserId();
        this.card_id = card.getCard_id();
        this.card_template = card.getCard_template();
        this.card_cover = card.getCard_cover();

        if (card.getAvatar() != null) {
            this.avatar = new AvatarResponse(
                    card.getAvatar().getFace(),
                    card.getAvatar().getHair(),
                    card.getAvatar().getHairColor(),
                    card.getAvatar().getClothes(),
                    card.getAvatar().getAcc(),
                    card.getAvatar().getBg(),
                    card.getAvatar().getBgColor()
            );
        }
        this.cardEssential = new CardEssential(card.getCard_name(), card.getCard_introduction());
        this.cardOptional = new CardOptional(card.getCard_SNS(), card.getCard_email(), card.getCard_MBTI(), card.getCard_music(), card.getCard_movie());

        if (card instanceof CardStudent) {
            CardStudent studentCard = (CardStudent) card;
            this.student = new CardStudentResponse(
                    studentCard.getCard_tel(),
                    studentCard.getCard_birth(),
                    studentCard.getCard_school(),
                    studentCard.getCard_grade(),
                    studentCard.getCard_student_major(),
                    studentCard.getCard_student_club(),
                    studentCard.getCard_student_role());
        } else if (card instanceof CardWorker) {
            CardWorker workerCard = (CardWorker) card;
            this.worker = new CardWorkerResponse(workerCard.getCard_tel(), workerCard.getCard_birth(), workerCard.getCard_job());
        }
    }

    // Getters and Setters
    public Long getCard_id() {
        return card_id;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCard_template() {
        return card_template;
    }

    public void setCard_template(String card_template) {
        this.card_template = card_template;
    }

    public String getCard_cover() {
        return card_cover;
    }

    public void setCard_cover(String card_cover) {
        this.card_cover = card_cover;
    }

    public Avatar getAvatar() {
        return "avatar".equals(this.card_cover) ? avatar : null;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
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

    public CardStudent getStudent() {
        return "student".equals(this.card_template) ? student : null;
    }

    public void setStudent(CardStudent student) {
        this.student = student;
    }

    public CardWorker getWorker() {
        return "worker".equals(this.card_template) ? worker : null;
    }

    public void setWorker(CardWorker worker) {
        this.worker = worker;
    }
}
