package SSOP.ssop.dto.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;

public class MemberResponse {

    private Long card_id;
    private Long team_sp_member_id;    // team_sp_member의 id
    private long user_id;

    private MemberEssentialDto memberEssential;
    private MemberOptionalDto memberOptional;
    private MemberStudentDto memberStudent;
    private MemberWorkerDto memberWorker;
    private MemberFanDto memberFan;

    public MemberResponse(Member member) {
        this.card_id = member.getCardId();
        this.team_sp_member_id = member.getTeamSpMember().getId();
        this.user_id = member.getTeamSpMember().getUser().getUserId();

        this.memberEssential = new MemberEssentialDto(
                member.getCard_name(),
                member.getCard_introduction(),
                member.getCard_cover(),
                member.getProfile_image_url()
        );

        this.memberOptional = new MemberOptionalDto(
                member.getCard_birth(),
                member.getCard_MBTI(),
                member.getCard_tel(),
                member.getCard_email(),
                member.getCard_insta(),
                member.getCard_x(),
                member.getCard_hobby(),
                member.getCard_music(),
                member.getCard_movie(),
                member.getCard_address(),
                member.getCard_free_1(),
                member.getCard_free_2(),
                member.getCard_free_3(),
                member.getCard_free_4(),
                member.getCard_free_5()
        );

        this.memberStudent = new MemberStudentDto(
                member.getCard_student_school(),
                member.getCard_student_grade(),
                member.getCard_student_major(),
                member.getCard_student_id(),
                member.getCard_student_club(),
                member.getCard_student_role(),
                member.getCard_student_status()
        );
        this.memberWorker = new MemberWorkerDto(
                member.getCard_worker_company(),
                member.getCard_worker_job(),
                member.getCard_worker_position(),
                member.getCard_worker_department()
        );

        this.memberFan = new MemberFanDto(
                member.getCard_fan_genre(),
                member.getCard_fan_first(),
                member.getCard_fan_second(),
                member.getCard_fan_reason()
        );
    }

    public Long getCard_id() {
        return card_id;
    }

    public Long getTeam_sp_member_id() {
        return team_sp_member_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public MemberEssentialDto getMemberEssential() {
        return memberEssential;
    }

    public MemberOptionalDto getMemberOptional() {
        return memberOptional;
    }

    public MemberStudentDto getMemberStudent() {
        return memberStudent;
    }

    public MemberWorkerDto getMemberWorker() {
        return memberWorker;
    }

    public MemberFanDto getMemberFan() {
        return memberFan;
    }
}
