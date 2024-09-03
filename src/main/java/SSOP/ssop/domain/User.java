package SSOP.ssop.domain;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @JsonProperty("user_name")
    private String user_name;
    private String email;
    private String password;
    private LocalDate user_birth;
    private String user_phone;
    private String social_type;

    @ElementCollection
    private Map<Long, LocalDateTime> saved_card_list = new HashMap<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TeamSpMember> teamSpMembers = new HashSet<>();

    // 의문.. @NoArgsConstructor 정의했지만 이 코드가 있어야 오류가 안난다고요?
    public User(){};

    public User(String user_name, String email, String password, String user_birth, String user_phone) {
        if( user_name == null || email == null || password == null || user_birth == null || user_phone == null ){
            throw new IllegalArgumentException();
        }
        this.user_name = user_name;
        this.email = email;
        this.password = password;

        // String 타입의 user_birth를 LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.user_birth = LocalDate.parse(user_birth, formatter);

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

    // Getter & Setter
    public long getUserId() {
        return userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public LocalDate getUser_birth() {
        return user_birth;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getSocial_type() {
        return social_type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_birth(LocalDate user_birth) {
        this.user_birth = user_birth;
    }

    public Map<Long, LocalDateTime> getSaved_card_list() {
        return saved_card_list;
    }

    public void setSaved_card_list(Map<Long, LocalDateTime> saved_card_list) {
        this.saved_card_list = saved_card_list;
    }

    public Set<TeamSpMember> getTeamSpMembers() {
        return teamSpMembers;
    }

    public void setTeamSpMembers(Set<TeamSpMember> teamSpMembers) {
        this.teamSpMembers = teamSpMembers;
    }
}
