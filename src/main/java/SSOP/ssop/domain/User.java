package SSOP.ssop.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    private long userId;

    private String user_name;
    private LocalDate user_birth;
    private String user_phone;
    private String password;
    private String email;
    private String social_type;

    @ElementCollection
    private List<String> saved_card_list;

    protected User() {};

    public User(String user_name, String user_birth, String user_phone, String password, String email) {
        if( user_name == null || user_birth == null || user_phone == null || password == null || email == null ){
            throw new IllegalArgumentException();
        }
        this.user_name = user_name;

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

    public void deleteSavedList(long card_id) { saved_card_list.remove(card_id); }

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

    public List<String> getSave_list() {
        return saved_card_list;
    }
}
