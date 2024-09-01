package SSOP.ssop.dto.card.response;

import SSOP.ssop.domain.card.Avatar;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvatarResponse extends Avatar {
    private int face;   // 얼굴
    private int hair;   // 헤어
    private int hairColor;  // 헤어 컬러
    private int clothes;    // 옷
    private int acc;        // 악세사리
    private int bg;         // 배경
    private int bgColor;    // 배경색

    public AvatarResponse(int face, int hair, int hairColor, int clothes, int acc, int bg, int bgColor) {
        this.face = face;
        this.hair = hair;
        this.hairColor = hairColor;
        this.clothes = clothes;
        this.acc = acc;
        this.bg = bg;
        this.bgColor = bgColor;
    }

    @Override
    public int getFace() {
        return face;
    }

    @Override
    public void setFace(int face) {
        this.face = face;
    }

    @Override
    public int getHair() {
        return hair;
    }

    @Override
    public void setHair(int hair) {
        this.hair = hair;
    }

    @Override
    public int getHairColor() {
        return hairColor;
    }

    @Override
    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public int getClothes() {
        return clothes;
    }

    @Override
    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    @Override
    public int getAcc() {
        return acc;
    }

    @Override
    public void setAcc(int acc) {
        this.acc = acc;
    }

    @Override
    public int getBg() {
        return bg;
    }

    @Override
    public void setBg(int bg) {
        this.bg = bg;
    }

    @Override
    public int getBgColor() {
        return bgColor;
    }

    @Override
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
