package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.Avatar;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarResponse extends Avatar {
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

    public AvatarResponse(int eyes, int eyebrows, int mouth, int mole, int hairFront, int hairBack, int hairFrontColor, int hairBackColor, int clothes, int accEar, int accNose, int accGlasses, int accPin, int accEtc, int bg, int bgColor) {
        this.eyes = eyes;
        this.eyebrows = eyebrows;
        this.mouth = mouth;
        this.mole = mole;
        this.hairFront = hairFront;
        this.hairBack = hairBack;
        this.hairFrontColor = hairFrontColor;
        this.hairBackColor = hairBackColor;
        this.clothes = clothes;
        this.accEar = accEar;
        this.accNose = accNose;
        this.accGlasses = accGlasses;
        this.accPin = accPin;
        this.accEtc = accEtc;
        this.bg = bg;
        this.bgColor = bgColor;
    }

    @Override
    public int getEyes() {
        return eyes;
    }

    @Override
    public int getEyebrows() {
        return eyebrows;
    }

    @Override
    public int getMouth() {
        return mouth;
    }

    @Override
    public int getMole() {
        return mole;
    }

    @Override
    public int getHairFront() {
        return hairFront;
    }

    @Override
    public int getHairBack() {
        return hairBack;
    }

    @Override
    public int getHairFrontColor() {
        return hairFrontColor;
    }

    @Override
    public int getHairBackColor() {
        return hairBackColor;
    }

    @Override
    public int getClothes() {
        return clothes;
    }

    @Override
    public int getAccEar() {
        return accEar;
    }

    @Override
    public int getAccNose() {
        return accNose;
    }

    @Override
    public int getAccGlasses() {
        return accGlasses;
    }

    @Override
    public int getAccPin() {
        return accPin;
    }

    @Override
    public int getAccEtc() {
        return accEtc;
    }

    @Override
    public int getBg() {
        return bg;
    }

    @Override
    public int getBgColor() {
        return bgColor;
    }

    @Override
    public void setEyes(int eyes) {
        this.eyes = eyes;
    }

    @Override
    public void setEyebrows(int eyebrows) {
        this.eyebrows = eyebrows;
    }

    @Override
    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    @Override
    public void setMole(int mole) {
        this.mole = mole;
    }

    @Override
    public void setHairFront(int hairFront) {
        this.hairFront = hairFront;
    }

    @Override
    public void setHairBack(int hairBack) {
        this.hairBack = hairBack;
    }

    @Override
    public void setHairFrontColor(int hairFrontColor) {
        this.hairFrontColor = hairFrontColor;
    }

    @Override
    public void setHairBackColor(int hairBackColor) {
        this.hairBackColor = hairBackColor;
    }

    @Override
    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    @Override
    public void setAccEar(int accEar) {
        this.accEar = accEar;
    }

    @Override
    public void setAccNose(int accNose) {
        this.accNose = accNose;
    }

    @Override
    public void setAccGlasses(int accGlasses) {
        this.accGlasses = accGlasses;
    }

    @Override
    public void setAccPin(int accPin) {
        this.accPin = accPin;
    }

    @Override
    public void setAccEtc(int accEtc) {
        this.accEtc = accEtc;
    }

    @Override
    public void setBg(int bg) {
        this.bg = bg;
    }

    @Override
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}