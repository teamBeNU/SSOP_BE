package SSOP.ssop.dto.card.request;

import SSOP.ssop.domain.card.Avatar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardUpdateRequest {
    private long card_id;

    private String card_name;
    private String card_introduction;
    private String card_template;
    private String card_cover;
    private Avatar avatar;
    private String profile_image_url;

    private String card_birth;
    private Boolean card_bSecret;
    private String card_tel;
    private String card_sns_insta;
    private String card_sns_x;
    private String card_email;
    private String card_MBTI;
    private String card_music;
    private String card_movie;
    private String card_hobby;
    private String card_address;

    private CardStudentUpdateRequest student;
    private CardWorkerUpdateRequest worker;
    private CardFanUpdateRequest fan;
}
