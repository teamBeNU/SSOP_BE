package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardEssentialCreateRequest {

    private String card_name;       // 이름
    private String card_introduction;       // 한줄소개
    private String card_template;       // 템플릿 종류(student, worker, fan, free)
    private String card_cover;     // 카드 커버(avatar, picture)
}
