package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.*;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponse {

    private Long user_id;
    private Long card_id;

    private String card_template;  // student or worker
    private String card_cover;

    private CardEssential cardEssential;
    private CardOptional cardOptional;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardStudent student;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardWorker worker;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CardFan fan;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Avatar avatar;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String profile_image_url;

    public CardResponse(Card card, CardStudent cardStudent, CardWorker cardWorker, CardFan cardFan) {
        this.user_id = card.getUserId();
        this.card_id = card.getCardId();
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

        if(card.getProfile_image_url() != null) {
            this.profile_image_url = card.getProfile_image_url();
        }

        this.cardEssential = new CardEssential(card.getCard_name(), card.getCard_introduction());
        this.cardOptional = new CardOptional(
                card.getCard_birth(),
                card.getCard_bSecrete(),
                card.getCard_tel(),
                card.getCard_sns_insta(),
                card.getCard_sns_x(),
                card.getCard_email(),
                card.getCard_MBTI(),
                card.getCard_music(),
                card.getCard_music(),
                card.getCard_movie(),
                card.getCard_hobby(),
                card.getCard_address()
        );

        if (card.getCard_template().equals("student")) {
            this.student = new CardStudentResponse(
                    cardStudent.getCard_student_school(),
                    cardStudent.getCard_student_grade(),
                    cardStudent.getCard_student_major(),
                    cardStudent.getCard_student_id(),
                    cardStudent.getCard_student_club(),
                    cardStudent.getCard_student_role(),
                    cardStudent.getCard_student_status());
        } else if (card.getCard_template().equals("worker")) {
            this.worker = new CardWorkerResponse(
                    cardWorker.getCard_worker_company(),
                    cardWorker.getCard_worker_job(),
                    cardWorker.getCard_worker_position(),
                    cardWorker.getCard_worker_department()
            );
        } else if (card.getCard_template().equals("fan")) {
            this.fan = new CardFanResponse(
                    cardFan.getCard_fan_genre(),
                    cardFan.getCard_fan_first(),
                    cardFan.getCard_fan_second(),
                    cardFan.getCard_fan_reason()
            );
        }
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getCard_id() {
        return card_id;
    }

    public void setCard_id(Long card_id) {
        this.card_id = card_id;
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
        return student;
    }

    public void setStudent(CardStudent student) {
        this.student = student;
    }

    public CardWorker getWorker() {
        return worker;
    }

    public void setWorker(CardWorker worker) {
        this.worker = worker;
    }

    public CardFan getFan() {
        return fan;
    }

    public void setFan(CardFan fan) {
        this.fan = fan;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }
}
