package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardResponse {

    private Long userId;
    private Long cardId;

    private LocalDateTime savedAt;
    private LocalDateTime updatedAt;

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

    private String memo;

    public CardResponse(Card card, CardStudent cardStudent, CardWorker cardWorker, CardFan cardFan, boolean isNotMyCard, LocalDateTime savedAt, LocalDateTime modifiedAt) {
        this.userId = card.getUserId();
        this.cardId = card.getCardId();
        this.savedAt = savedAt;
        this.updatedAt = modifiedAt;
        this.card_template = card.getCard_template();
        this.card_cover = card.getCard_cover();

        if(isNotMyCard) {
            this.memo = card.getMemo();
        }

        if (card.getAvatar() != null) {
            this.avatar = new AvatarResponse(
                    card.getAvatar().getEyes(),
                    card.getAvatar().getEyebrows(),
                    card.getAvatar().getMouth(),
                    card.getAvatar().getMole(),
                    card.getAvatar().getHairFront(),
                    card.getAvatar().getHairBack(),
                    card.getAvatar().getHairFrontColor(),
                    card.getAvatar().getHairBackColor(),
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
                card.getCard_bSecret(),
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

        if (card.getCard_template().equals("student") ||
            card.getCard_template().equals("studentSchool") ||
            card.getCard_template().equals("studentUniv")) {
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
        } else if (card.getCard_template().equals("free")) {
            if (cardStudent != null) {
                this.student = new CardStudentResponse(
                        cardStudent.getCard_student_school(),
                        cardStudent.getCard_student_grade(),
                        cardStudent.getCard_student_major(),
                        cardStudent.getCard_student_id(),
                        cardStudent.getCard_student_club(),
                        cardStudent.getCard_student_role(),
                        cardStudent.getCard_student_status()
                );
            }
            if (cardWorker != null) {
                this.worker = new CardWorkerResponse(
                        cardWorker.getCard_worker_company(),
                        cardWorker.getCard_worker_job(),
                        cardWorker.getCard_worker_position(),
                        cardWorker.getCard_worker_department())
                ;
            }
            if (cardFan != null) {
                this.fan = new CardFanResponse(
                        cardFan.getCard_fan_genre(),
                        cardFan.getCard_fan_first(),
                        cardFan.getCard_fan_second(),
                        cardFan.getCard_fan_reason()
                );
            }
        }
    }
}