package SSOP.ssop.domain.TeamSp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Embeddable
@Getter
@Setter
public class Filter {

    @ElementCollection
    @CollectionTable(name = "filter_role", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "role")
    private List<String> role;

    @ElementCollection
    @CollectionTable(name = "filter_major", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "major")
    private List<String> major;

    @ElementCollection
    @CollectionTable(name = "filter_position", joinColumns = @JoinColumn(name = "team_id"))
    @Column(name = "position")
    private List<String> position;
}