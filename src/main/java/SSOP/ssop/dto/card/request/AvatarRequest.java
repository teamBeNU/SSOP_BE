package SSOP.ssop.dto.card.request;

public class AvatarRequest {

    private int face;
    private int hair;
    private int hairColor;
    private int clothes;
    private int acc;
    private int bg;
    private int bgColor;

    public int getFace() {
        return face;
    }

    public int getHair() {
        return hair;
    }

    public int getHairColor() {
        return hairColor;
    }

    public int getClothes() {
        return clothes;
    }

    public int getAcc() {
        return acc;
    }

    public int getBg() {
        return bg;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setFace(int face) {
        this.face = face;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public void setClothes(int clothes) {
        this.clothes = clothes;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
