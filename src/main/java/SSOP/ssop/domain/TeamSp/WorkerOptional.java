package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}


