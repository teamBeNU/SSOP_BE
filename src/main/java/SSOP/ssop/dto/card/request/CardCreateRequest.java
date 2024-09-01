package SSOP.ssop.dto.card.request;

import SSOP.ssop.domain.card.Avatar;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardCreateRequest {

    private CardEssentialCreateRequest cardEssential;
    private CardOptionalCreateRequest cardOptional;

    private Avatar avatar;  // card_cover가 avatar일 때만 사용

    private CardStudentCreateRequest student;
    private CardWorkerCreateRequest worker;
    private CardFanCreateRequest fan;
}
