package SSOP.ssop.dto.TeamSp;

public class MemberDto {

    private MemberEssentialDto memberEssential;
    private MemberOptionalDto memberOptional;
    private MemberStudentDto memberStudent;
    private MemberWorkerDto memberWorker;
    private MemberFanDto memberFan;

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
