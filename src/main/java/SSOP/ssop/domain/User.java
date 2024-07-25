package SSOP.ssop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String user_name;
    private String user_birth;
    private String user_phone;
    private String password;
    private String email;
    private String social_type;

    public String getUser_name() {
        return user_name;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getUser_birth() {
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
}
