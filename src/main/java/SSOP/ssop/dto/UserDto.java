package SSOP.ssop.dto;

import SSOP.ssop.domain.User;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class UserDto {
    private Long userId;
    private String user_name;
    private String email;
    private String password;
    private LocalDate user_birth;
    private String user_phone;
    private String social_type;

    public UserDto() {
    }

    public UserDto(User user) {
        this.userId = user.getUserId();
        this.user_name = user.getUser_name();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.user_birth = user.getUser_birth();
        this.user_phone = user.getUser_phone();
        this.social_type = user.getSocial_type();
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDate getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(LocalDate user_birth) {
        this.user_birth = user_birth;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocial_type() {
        return social_type;
    }

    public void setSocial_type(String social_type) {
        this.social_type = social_type;
    }
}
