package SSOP.ssop.dto.TeamSp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberEssentialDto {
    private String card_name;   // 이름
    private String card_introduction;   // 한줄소개
    private String card_template; // 템플릿 종류
    private String card_cover;     // 카드 커버(free, avatar, picture)
    private String profile_image_url;   // 이미지

//    public MemberEssentialDto(String card_name, String card_introduction, String card_template, String card_cover, String profile_image_url) {
//        this.card_name = card_name;
//        this.card_introduction = card_introduction;
//        this.card_template = card_template;
//        this.card_cover = card_cover;
//        this.profile_image_url = profile_image_url;
//    }
//
//    public String getCard_name() {
//        return card_name;
//    }
//
//    public String getCard_introduction() {
//        return card_introduction;
//    }
//
//    public String getCard_template() {return card_template;}
//
//    public String getCard_cover() {
//        return card_cover;
//    }
//
//    public String getProfile_image_url() {
//        return profile_image_url;
//    }
//
//    public void setCard_name(String card_name) {
//        this.card_name = card_name;
//    }
//
//    public void setCard_introduction(String card_introduction) {
//        this.card_introduction = card_introduction;
//    }
//
//    public void setCard_template(String card_template) { this.card_template = card_template; }
//
//    public void setCard_cover(String card_cover) {
//        this.card_cover = card_cover;
//    }
//
//    public void setProfile_image_url(String profile_image_url) {
//        this.profile_image_url = profile_image_url;
//    }
}
