package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FanOptional {

    @Column(name = "fan_showGenre")
    private Boolean showGenre;

    @Column(name = "fan_showFavorite")
    private Boolean showFavorite;

    @Column(name = "fan_showSecond")
    private Boolean showSecond;

    @Column(name = "fan_showReason")
    private Boolean showReason;

    public boolean checkNullValue() {
        return showGenre == null && showFavorite == null && showSecond == null && showReason == null;
    }

    public Boolean getShowGenre() {
        return showGenre;
    }

    public Boolean getShowFavorite() {
        return showFavorite;
    }

    public Boolean getShowSecond() {
        return showSecond;
    }

    public Boolean getShowReason() {
        return showReason;
    }
}
