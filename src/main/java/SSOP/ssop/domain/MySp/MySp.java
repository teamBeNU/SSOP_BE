package SSOP.ssop.domain.MySp;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class MySp {

    @Id
    @Column(name = "groupId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @Column(nullable = false, name = "group_name")
    private String group_name;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    // 카드 리스트 조회 메서드
    // 그룹에 속한 카드 리스트
    @Getter
    @OneToMany(mappedBy = "mySp", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
    private List<Card> cards = new ArrayList<>();

    public MySp() {}

    public MySp(User user, String group_name) {
        this.user = user;
        this.group_name = group_name;
    }

    // 카드 추가 메서드
    public void addCard(Card card) {
        this.cards.add(card);
        card.setMySp(this);
    }

    // 카드 제거 메서드
    public void removeCard(Card card) {
        this.cards.remove(card);  // 그룹의 카드 목록에서 제거
        card.setMySp(null);       // 카드의 그룹 연관관계 해제
    }
}