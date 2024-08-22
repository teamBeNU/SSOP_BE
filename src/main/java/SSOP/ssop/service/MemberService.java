package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.dto.card.TeamSp.*;
import SSOP.ssop.repository.MemberRepository;
import SSOP.ssop.repository.TeamSpMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class MemberService {

    private MemberRepository memberRepository;
    private TeamSpMemberRepository teamSpMemberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, TeamSpMemberRepository teamSpMemberRepository) {
        this.memberRepository = memberRepository;
        this.teamSpMemberRepository = teamSpMemberRepository;
    }

    // 카드 생성
    @Transactional
    public void saveMember(Long teamId, Long userId, MemberDto memberDto, MultipartFile file) throws Exception {
        TeamSpMember teamSpMember = teamSpMemberRepository.findByTeamSpIdAndUserId(teamId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀 스페이스입니다."));

        boolean memberExist = memberRepository.existsByTeamSpMemberId(teamSpMember.getId());
        if (memberExist) {
            throw new IllegalArgumentException("이미 존재하는 멤버입니다.");
        }

        String profileImageUrl = null;
        if (file != null && !file.isEmpty()) {
            profileImageUrl = saveImage(file, teamId);
        }

        Member member = new Member();

        member.setTeamSpMember(teamSpMember);
        member.setProfile_image_url(profileImageUrl);
        // member.setProfile_image_url(profileImageUrl != null ? profileImageUrl : "default-profile-image-url"); // 기본 이미지 URL 설정

        setMemberDto(member, memberDto);

        memberRepository.save(member);
    }

    private String saveImage(MultipartFile file, Long teamId) throws Exception {

        String projectRootPath = new File("").getAbsolutePath();    // 프로젝트 폴더의 절대 경로
        String relativePath = "/src/main/resources/static/uploads/teamSp/";    // 이미지 저장 경로 설정 (로컬 경로)
        String uploadDir = projectRootPath + relativePath + teamId;

        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리가 존재하지 않으면 생성
        }

        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + file.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)

        // 파일 저장
        File saveFile = new File(directory, fileName);
        file.transferTo(saveFile);  // 파일 저장

        return "/uploads/teamSp/" + teamId + "/" + fileName;
    }

    private void setMemberDto(Member member, MemberDto memberDto) {
        if (memberDto == null) {
            return;
        }

        MemberEssentialDto essentialDto = memberDto.getMemberEssential();
        if (essentialDto != null) {
            member.setCard_name(essentialDto.getCard_name());
            member.setCard_introduction(essentialDto.getCard_introduction());
            member.setCard_cover(essentialDto.getCard_cover());
        }

        MemberOptionalDto optionalDto = memberDto.getMemberOptional();
        if (optionalDto != null) {
            member.setCard_age(optionalDto.getCard_age());
            member.setCard_birth(optionalDto.getCard_birth());
            member.setCard_MBTI(optionalDto.getCard_MBTI());
            member.setCard_tel(optionalDto.getCard_tel());
            member.setCard_email(optionalDto.getCard_email());
            member.setCard_insta(optionalDto.getCard_insta());
            member.setCard_x(optionalDto.getCard_x());
            member.setCard_hobby(optionalDto.getCard_hobby());
            member.setCard_music(optionalDto.getCard_music());
            member.setCard_movie(optionalDto.getCard_movie());
            member.setCard_address(optionalDto.getCard_address());
            member.setCard_free_1(optionalDto.getCard_free_A1());
            member.setCard_free_2(optionalDto.getCard_free_A2());
            member.setCard_free_3(optionalDto.getCard_free_A3());
            member.setCard_free_4(optionalDto.getCard_free_A4());
            member.setCard_free_5(optionalDto.getCard_free_A5());
        }

        MemberStudentDto studentDto = memberDto.getMemberStudent();
        if (studentDto != null) {
            member.setCard_student_school(studentDto.getCard_student_school());
            member.setCard_student_grade(studentDto.getCard_student_grade());
            member.setCard_student_id(studentDto.getCard_student_id());
            member.setCard_student_major(studentDto.getCard_student_major());
            member.setCard_student_club(studentDto.getCard_student_club());
            member.setCard_student_role(studentDto.getCard_student_role());
        }

        MemberWorkerDto workerDto = memberDto.getMemberWorker();
        if (workerDto != null) {
            member.setCard_worker_company(workerDto.getCard_worker_company());
            member.setCard_worker_job(workerDto.getCard_worker_job());
            member.setCard_worker_position(workerDto.getCard_worker_position());
            member.setCard_worker_department(workerDto.getCard_worker_department());
        }

        MemberFanDto fanDto = memberDto.getMemberFan();
        if (fanDto != null) {
            member.setCard_fan_genre(fanDto.getCard_fan_genre());
            member.setCard_fan_first(fanDto.getCard_fan_first());
            member.setCard_fan_second(fanDto.getCard_fan_second());
            member.setCard_fan_reason(fanDto.getCard_fan_reason());
        }
    }
}
