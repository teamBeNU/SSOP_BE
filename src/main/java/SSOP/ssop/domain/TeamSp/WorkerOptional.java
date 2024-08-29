package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkerOptional {

    @Column(name = "worker_showCompany")
    private Boolean showCompany;

    @Column(name = "worker_showJob")
    private Boolean showJob;

    @Column(name = "worker_showPosition")
    private Boolean showPosition;

    @Column(name = "worker_showPart")
    private Boolean showPart;

    public boolean checkNullValue() {
        return showCompany == null && showJob == null && showPosition == null && showPart == null;
    }

    public Boolean getShowCompany() {
        return showCompany;
    }

    public Boolean getShowJob() {
        return showJob;
    }

    public Boolean getShowPosition() {
        return showPosition;
    }

    public Boolean getShowPart() {
        return showPart;
    }
}