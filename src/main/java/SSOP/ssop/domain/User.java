package SSOP.ssop.domain;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String role; // "ADMIN" or "USER"

    private String user_name;
    private String email;
    private String password;
    private String user_birth;
    private String user_phone;
    private String social_type;

    @ElementCollection
    private Map<Long, LocalDateTime> saved_card_list = new HashMap<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamSpMember> teamSpMembers = new HashSet<>();

    // 사용자가 소유한 카드 목록 (카드를 보낸 목록)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> sentCards = new HashSet<>();

    // 사용자가 수신한 카드 목록 (카드를 받은 목록)
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> receivedCards = new HashSet<>();

    public User(String user_name, String email, String password, String user_birth, String user_phone) {
        if( user_name == null || email == null || password == null || user_birth == null || user_phone == null ){
            throw new IllegalArgumentException();
        }
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.user_birth = user_birth;
        this.user_phone = user_phone;
        this.password = password;
        this.email = email;
        this.social_type = "default";
    }

    public User(long userId) {
        this.userId = userId;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void deleteSavedList(long cardId) { saved_card_list.remove(cardId); }

    public void enterTeamSp(TeamSp teamSp) {
        this.teamSpMembers.add(new TeamSpMember(teamSp, this, null));
    }

}
