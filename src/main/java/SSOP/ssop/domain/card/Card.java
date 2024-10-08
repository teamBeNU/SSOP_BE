package SSOP.ssop.domain.card;

import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "card")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private Long userId;

    // 카드 소유자 (카드를 보낸 사람), userId와 관계 설정
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;  // 외래 키는 userId와 연결되지만, 중복 입력되지 않도록 함

    // 카드 수신자 (카드를 받은 사람)
    @ManyToOne
    @JoinColumn(name = "recipientId")
    private User recipient;

    // 공통 필수
    @Column(nullable = false, length = 20)
    private String card_name;       // 이름

    @Column(nullable = false)
    private String card_introduction;       // 한줄소개

    @Column(nullable = false)
    private String card_template;       // 템플릿 종류(student, worker, fan, free)

    @Column(nullable = false)
    private String card_cover;     // 카드 커버(avatar, picture)

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private Avatar avatar;  // card_cover가 avatar일 때만 사용

    @Column(nullable = false)
    private String profile_image_url;   // 이미지

    // 공통 선택
    private String card_birth;      // 생년월일

    private Boolean card_bSecret;      // 생년월일 비밀

    private String card_tel;    // 연락처

    private String card_sns_insta;  // sns: insta

    private String card_sns_x;      // sns: x

    private String card_email;      // 이메일

    private String card_MBTI;       // mbti

    private String card_music;      // 음악

    private String card_movie;      // 영화

    private String card_hobby;      // 취미

    private String card_address;        // 거주지

    private String memo;            // 메모

    @CreationTimestamp // 카드 생성 시간
    @Column(name = "createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private CardFan cardFan;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private CardStudent cardStudent;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private CardWorker cardWorker;

    // MySp와의 다대일 관계 추가
    @ManyToOne
    @JoinColumn(name = "groupId")
    private MySp mySp;  // 카드가 속한 그룹

    private String status;  // 카드 상태 ("요청 중...", "공유 완료됨")

    public Card() {}

    @Builder
    public Card(String card_name, String card_introduction, String card_template, String card_cover, Avatar avatar, String profile_image_url, String card_birth, Boolean card_bSecret, String card_tel, String card_sns_insta, String card_sns_x, String card_email, String card_MBTI, String card_music, String card_movie, String card_hobby, String card_address) {
        if (card_name == null || card_name.isBlank() ||
                card_introduction == null || card_introduction.isBlank() ||
                card_template == null || card_cover == null || profile_image_url == null) {
            throw new IllegalArgumentException();
        }
        this.card_name = card_name;
        this.card_introduction = card_introduction;
        this.card_template = card_template;
        this.card_cover = card_cover;
        this.avatar = avatar;
        this.profile_image_url = profile_image_url;
        this.card_birth = card_birth;
        this.card_bSecret = card_bSecret;
        this.card_tel = card_tel;
        this.card_sns_insta = card_sns_insta;
        this.card_sns_x = card_sns_x;
        this.card_email = card_email;
        this.card_MBTI = card_MBTI;
        this.card_music = card_music;
        this.card_movie = card_movie;
        this.card_hobby = card_hobby;
        this.card_address = card_address;
    }
}
