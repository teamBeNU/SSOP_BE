package SSOP.ssop.dto;

import SSOP.ssop.domain.User;

import java.time.LocalDate;

// 어노테이션 추가하기
public class UserDto {
    private String user_name;
    private Long user_id;
    private LocalDate user_birth;
    private String user_phone;
    private String password;
    private String email;
    private String social_type;

    public UserDto(long user_id, String user_name, LocalDate user_birth, String user_tel, String password, String email, String social_type) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_birth = user_birth;
        this.user_phone = user_phone;
        this.password = password;
        this.email = email;
        this.social_type = social_type;
    }

    public UserDto(User user) {
        this.user_id = user.getUser_id();
        this.user_name = user.getUser_name();
        this.user_birth = user.getUser_birth();
        this.user_phone = user.getUser_phone();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.social_type = user.getSocial_type();
    }

    public String getUser_name() {
        return user_name;
    }

    public Long getUser_id() {
        return user_id;
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
}
