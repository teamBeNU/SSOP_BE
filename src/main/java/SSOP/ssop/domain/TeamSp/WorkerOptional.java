package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerOptional {

    @Column(name = "worker_showAge")
    private Boolean showAge;

    @Column(name = "worker_showHobby")
    private Boolean showHobby;

    @ElementCollection
    @CollectionTable(name = "team_roles", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "worker_showRole")
    private List<String> showRole;

    @Column(name = "worker_showTel")
    private Boolean showTel;

    @Column(name = "worker_showSNS")
    private Boolean showSNS;

    @Column(name = "worker_showEmail")
    private Boolean showEmail;

    public boolean checkNullValue() {
        return showAge == null &&
                (showRole == null || showRole.isEmpty()) && showTel == null &&
                showSNS == null && showEmail == null && showHobby == null;
    }

    public Boolean getShowAge() {
        return showAge;
    }

    public void setShowAge(Boolean showAge) {
        this.showAge = showAge;
    }

    public Boolean getShowHobby() {
        return showHobby;
    }

    public void setShowHobby(Boolean showHobby) {
        this.showHobby = showHobby;
    }

    public List<String> getShowRole() {
        return showRole;
    }

    public void setShowRole(List<String> showRole) {
        this.showRole = showRole;
    }

    public Boolean getShowTel() {
        return showTel;
    }

    public void setShowTel(Boolean showTel) {
        this.showTel = showTel;
    }

    public Boolean getShowSNS() {
        return showSNS;
    }

    public void setShowSNS(Boolean showSNS) {
        this.showSNS = showSNS;
    }

    public Boolean getShowEmail() {
        return showEmail;
    }

    public void setShowEmail(Boolean showEmail) {
        this.showEmail = showEmail;
    }
}