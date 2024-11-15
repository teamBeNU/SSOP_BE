package SSOP.ssop.dto.card.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvatarRequest {

    private int eyes;   // 얼굴 - 눈
    private int eyebrows;   // 얼굴 - 눈썹
    private int mouth;   // 얼굴 - 입
    private int mole;       // 이목구비 - 점
    private int hairFront;   // 헤어 - 앞머리
    private int hairBack;   // 헤어 - 뒷머리
    private int hairFrontColor;  // 헤어 - 앞머리 컬러
    private int hairBackColor;  // 헤어 - 뒷머리 컬러
    private int clothes;    // 옷
    private int accEar;     // 악세사리 - 귀걸이
    private int accNose;     // 악세사리 - 코 피어싱
    private int accGlasses;     // 악세사리 - 안경
    private int accPin;     // 악세사리 - 머리핀
    private int accEtc;     // 악세사리 - 기타
    private int bg;         // 배경
    private int bgColor;    // 배경색
}
