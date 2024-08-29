package SSOP.ssop.domain.TeamSp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class TeamSp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long team_id;

    private Long host_id; // 호스트 ID 저장

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

    @Embedded
    private StudentOptional studentOptional;

    @Embedded
    private WorkerOptional workerOptional;

    @Embedded
    private FanOptional fanOptional;

    @OneToMany(mappedBy = "teamSp", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<TeamSpMember> members;

    // 기본 생성자
    protected TeamSp() {}

    // 전체 필드를 초기화 생성자
    public TeamSp(String team_name, String team_comment, Boolean isTemplate, String template,
                  StudentOptional studentOptional, WorkerOptional workerOptional) {
        this.team_name = team_name;
        this.team_comment = team_comment;
        this.isTemplate = isTemplate;
        this.template = template;
        setStudentOptional(studentOptional);
        setWorkerOptional(workerOptional);
    }

    // Template 값에 따라 변수 반환
    public StudentOptional getStudentOptional() {
        if ("student".equals(template)) {
            return (studentOptional != null && studentOptional.checkNullValue()) ? null : studentOptional;
        }
        return null;
    }

    // Template이 "worker"일 때만 WorkerOptional을 반환
    public WorkerOptional getWorkerOptional() {
        if ("worker".equals(template)) {
            return (workerOptional != null && workerOptional.checkNullValue()) ? null : workerOptional;
        }
        return null;
    }

    // template에 따라 필요한 Optional 객체만 설정
    public void setStudentOptional(StudentOptional studentOptional) {
        if ("student".equals(template)) {
            this.studentOptional = studentOptional;
            this.workerOptional = null;
        }
    }

    public void setWorkerOptional(WorkerOptional workerOptional) {
        if ("worker".equals(template)) {
            this.workerOptional = workerOptional;
            this.studentOptional = null;
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

    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }

    public Long getHost_id() {
        return host_id;
    }

    public void setHost_id(Long host_id) {
        this.host_id = host_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_comment() {
        return team_comment;
    }

    public void setTeam_comment(String team_comment) {
        this.team_comment = team_comment;
    }

    public Boolean getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(Boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(int inviteCode) {
        this.inviteCode = inviteCode;
    }

    public List<TeamSpMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamSpMember> members) {
        this.members = members;
    }
}
