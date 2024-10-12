package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TeamSp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamId")
    private Long teamId;

    private Long hostId; // 호스트 ID 저장

    @Column(name = "team_name")
    private String team_name;

    @Column(name = "team_comment")
    private String team_comment;

    @Column(name = "isTemplate")
    private Boolean isTemplate;

    @Column(name = "template")
    private String template;

    @Column(name = "inviteCode")
    private int inviteCode;

    @Column(name = "showAge")
    private Boolean showAge;      // 나이

    @Column(name = "showBirth")
    private Boolean showBirth;    // 생년월일

    @Column(name = "showMBTI")
    private Boolean showMBTI;     // mbti

    @Column(name = "showTel")
    private Boolean showTel;      // 연락처

    @Column(name = "showEmail")
    private Boolean showEmail;    // 이메일

    @Column(name = "showInsta")
    private Boolean showInsta;    // 인스타

    @Column(name = "showX")
    private Boolean showX;        // x(트위터)

    @Embedded
    private StudentOptional studentOptional;

    @Embedded
    private WorkerOptional workerOptional;

    @Embedded
    private FanOptional fanOptional;

    @Column(name = "showHobby")
    private Boolean showHobby;        // 취미

    @Column(name = "showMusic")
    private Boolean showMusic;       // 인생음악

    @Column(name = "showMovie")
    private Boolean showMovie;      // 인생영화

    @Column(name = "showAddress")
    private Boolean showAddress;   // 거주지

    @ElementCollection
    @CollectionTable(name = "plus", joinColumns = @JoinColumn(name = "teamId"))
    @Column(name = "plus")
    private List<String> plus;    // 자유 선택지

    @Column(name = "cardCover")
    private String cardCover;         // 카드 커버 (자유/아바타/사진)

    @OneToMany(mappedBy = "teamSp", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TeamSpMember> members;

    // Template 값에 따라 변수 반환
    public StudentOptional getStudentOptional() {
        if ("student".equals(template) || "free".equals(template)) {
            return (studentOptional != null && studentOptional.checkNullValue()) ? null : studentOptional;
        }
        return null;
    }

    public WorkerOptional getWorkerOptional() {
        if ("worker".equals(template) || "free".equals(template)) {
            return (workerOptional != null && workerOptional.checkNullValue()) ? null : workerOptional;
        }
        return null;
    }

    public FanOptional getFanOptional() {
        if ("fan".equals(template) || "free".equals(template)) {
            return (fanOptional != null && fanOptional.checkNullValue()) ? null : fanOptional;
        }
        return null;
    }

    // template에 따라 필요한 Optional 객체만 설정
    public void setStudentOptional(StudentOptional studentOptional) {
        if ("student".equals(template) || "free".equals(template)) {
            this.studentOptional = studentOptional;
            if ("student".equals(template)) {
                this.workerOptional = null;
                this.fanOptional = null;
            }
        }
    }

    public void setWorkerOptional(WorkerOptional workerOptional) {
        if ("worker".equals(template) || "free".equals(template)) {
            this.workerOptional = workerOptional;
            if ("worker".equals(template)) {
                this.studentOptional = null;
                this.fanOptional = null;
            }
        }
    }

    public void setFanOptional(FanOptional fanOptional) {
        if ("fan".equals(template) || "free".equals(template)) {
            this.fanOptional = fanOptional;
            if ("fan".equals(template)) {
                this.studentOptional = null;
                this.workerOptional = null;
            }
        }
    }

    public void setFreeOptional(StudentOptional studentOptional, WorkerOptional workerOptional, FanOptional fanOptional) {
        if ("free".equals(template)) {
            this.studentOptional = studentOptional;
            this.workerOptional = workerOptional;
            this.fanOptional = fanOptional;
        }
    }

    public void updateTeamName(String team_name) {
        this.team_name = team_name;
    }

    // 팀스페이스 유저 퇴장
    public void removeMember(TeamSpMember member) {
        this.members.remove(member);
        member.setTeamSp(null); // 참조를 제거
    }
}