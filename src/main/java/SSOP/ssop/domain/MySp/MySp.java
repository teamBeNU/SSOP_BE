package SSOP.ssop.domain.MySp;

import SSOP.ssop.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class MySp {

    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(nullable = false, name = "group_name")
    private String group_name;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    public MySp() {}

    public MySp(User user, String group_name) {
        this.user = user;
        this.group_name = group_name;
    }
}
