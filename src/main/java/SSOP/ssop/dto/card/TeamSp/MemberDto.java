package SSOP.ssop.dto.card.TeamSp;

public class MemberDto {
//    private Long cardId;
//    private Long teamSpMemberId;

    private MemberEssentialDto memberEssential;
    private MemberOptionalDto memberOptional;
    private MemberStudentDto memberStudent;
    private MemberWorkerDto memberWorker;
    private MemberFanDto memberFan;

//    public Long getCardId() {
//        return cardId;
//    }
//
//    public Long getTeamSpMemberId() {
//        return teamSpMemberId;
//    }

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
