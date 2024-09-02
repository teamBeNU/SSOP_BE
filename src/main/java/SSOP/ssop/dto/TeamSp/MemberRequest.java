package SSOP.ssop.dto.TeamSp;

public class MemberRequest {

    private MemberEssentialDto memberEssential;
    private MemberOptionalDto memberOptional;
    private MemberStudentDto memberStudent;
    private MemberWorkerDto memberWorker;
    private MemberFanDto memberFan;

    public MemberRequest(MemberEssentialDto memberEssential, MemberOptionalDto memberOptional, MemberStudentDto memberStudent, MemberWorkerDto memberWorker, MemberFanDto memberFan) {
        this.memberEssential = memberEssential;
        this.memberOptional = memberOptional;
        this.memberStudent = memberStudent;
        this.memberWorker = memberWorker;
        this.memberFan = memberFan;
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
