package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.Avatar;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarResponse extends Avatar {
    private int eyes;   // 얼굴 - 눈
    private int eyebrows;   // 얼굴 - 눈썹
    private int mouth;   // 얼굴 - 입
    private int hairFront;   // 헤어 - 앞머리
    private int hairBack;   // 헤어 - 뒷머리
    private int hairFrontColor;  // 헤어 - 앞머리 컬러
    private int hairBackColor;  // 헤어 - 뒷머리 컬러
    private int clothes;    // 옷
    private int acc;        // 악세사리
    private int bg;         // 배경
    private int bgColor;    // 배경색

    public AvatarResponse(int eyes, int eyebrows, int mouth, int hairFront, int hairBack, int hairFrontColor, int hairBackColor, int clothes, int acc, int bg, int bgColor) {
        this.eyes = eyes;
        this.eyebrows = eyebrows;
        this.mouth = mouth;
        this.hairFront = hairFront;
        this.hairBack = hairBack;
        this.hairFrontColor = hairFrontColor;
        this.hairBackColor = hairBackColor;
        this.clothes = clothes;
        this.acc = acc;
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
    public int getAcc() {
        return acc;
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
    public void setAcc(int acc) {
        this.acc = acc;
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
