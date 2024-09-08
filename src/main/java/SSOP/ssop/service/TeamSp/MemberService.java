package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.dto.TeamSp.*;
import SSOP.ssop.repository.TeamSp.MemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MemberService {

    private MemberRepository memberRepository;
    private TeamSpMemberRepository teamSpMemberRepository;

    @Autowired
    private S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private String TEAMSP_IMG_DIR = "teamsp/";

    @Autowired
    public MemberService(MemberRepository memberRepository, TeamSpMemberRepository teamSpMemberRepository) {
        this.memberRepository = memberRepository;
        this.teamSpMemberRepository = teamSpMemberRepository;
    }

    // 팀스페이스 멤버 카드 생성 (지정 템플릿)
    @Transactional
    public void saveMember(Long teamId, Long userId, MemberRequest memberRequest, MultipartFile file) throws Exception {
        TeamSpMember teamSpMember = teamSpMemberRepository.findByTeamSpIdAndUserId(teamId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않습니다."));

        boolean memberExist = memberRepository.existsByTeamSpMemberId(teamSpMember.getId());
        if (memberExist) {
            throw new IllegalArgumentException("이미 존재하는 멤버입니다.");
        }

        String profileImageUrl = null;
        if (file != null && !file.isEmpty()) {
            profileImageUrl = uploadImage(file, teamId, userId);
        }

        Member member = new Member();

        member.setTeamSpMember(teamSpMember);
        member.setProfile_image_url(profileImageUrl);
        // member.setProfile_image_url(profileImageUrl != null ? profileImageUrl : "default-profile-image-url"); // 기본 이미지 URL 설정

        setMemberRequest(member, memberRequest);

        memberRepository.save(member);

        // teamSpMember의 cardId를 null에서 0으로 변경
        if (teamSpMember.getCardId() == null) {
            teamSpMember.setCardId(0L);
            teamSpMember.setCreatedAt(LocalDateTime.now()); // 지정 템플릿 카드 생성시간으로 업데이트
            teamSpMemberRepository.save(teamSpMember);
        } else {
            throw new IllegalArgumentException("이미 카드가 생성된 사용자입니다.");
        }
    }

/*    private String saveImage(MultipartFile file, Long teamId) throws Exception {

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
    }*/

    // 이미지 업로드 (ASW S3 업로드)
    private String uploadImage(MultipartFile multipartFile, Long teamId, Long userId) throws IOException {
        UUID uuid = UUID.randomUUID();  // 랜덤 uuid 값 생성
        String fileName = uuid + "_" + multipartFile.getOriginalFilename();  // 저장할 파일 이름(uuid_원본파일이름)
        String filePath = TEAMSP_IMG_DIR + teamId + "/" + userId + "_" + fileName;   // 저장할 파일 경로

        File uploadFile = convertMultipartFileToFile(multipartFile)     // multipartFile을 file로 변환
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File 변환 실패"));

        // S3에 파일 업로드
        String fileUrl = uploadFileToS3(filePath, uploadFile);

        // 임시로 생성한 로컬 파일 삭제
        removeFile(uploadFile);

        return fileUrl;
    }

    // S3로 파일 업로드
    private String uploadFileToS3(String filePath, File uploadFile) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(filePath)   // S3 내 디렉토리 및 파일 이름 설정
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(uploadFile));     // S3 업로드

        // 파일이 업로드된 S3의 URL 반환
        return s3Client.utilities().getUrl(builder -> builder.bucket(bucket).key(filePath)).toExternalForm();
    }

    // 임시로 생성한 로컬 파일 삭제
    private void removeFile(File file) {
        if (file.delete()) {
            log.info("임시 로컬 파일 삭제 성공: {}", file);
            return;
        }
        log.info("임시 로컬 파일 삭제 실패: {}", file);
    }

    // MultipartFile을 File로 변환
    private Optional<File> convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());   // 운영체제의 임시 디렉토리 경로 가져옴(C:\Users\{사용자명}\AppData\Local\Temp\)
        try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
            fos.write(file.getBytes());
        }
        return Optional.of(convertFile);
    }

    private void setMemberRequest(Member member, MemberRequest memberRequest) {
        if (memberRequest == null) {
            return;
        }

        MemberEssentialDto essentialDto = memberRequest.getMemberEssential();
        if (essentialDto != null) {
            member.setCard_name(essentialDto.getCard_name());
            member.setCard_introduction(essentialDto.getCard_introduction());
            member.setCard_cover(essentialDto.getCard_cover());
        }

        MemberOptionalDto optionalDto = memberRequest.getMemberOptional();
        if (optionalDto != null) {
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

        MemberStudentDto studentDto = memberRequest.getMemberStudent();
        if (studentDto != null) {
            member.setCard_student_school(studentDto.getCard_student_school());
            member.setCard_student_grade(studentDto.getCard_student_grade());
            member.setCard_student_id(studentDto.getCard_student_id());
            member.setCard_student_major(studentDto.getCard_student_major());
            member.setCard_student_club(studentDto.getCard_student_club());
            member.setCard_student_role(studentDto.getCard_student_role());
            member.setCard_student_status(studentDto.getCard_student_status());
        }

        MemberWorkerDto workerDto = memberRequest.getMemberWorker();
        if (workerDto != null) {
            member.setCard_worker_company(workerDto.getCard_worker_company());
            member.setCard_worker_job(workerDto.getCard_worker_job());
            member.setCard_worker_position(workerDto.getCard_worker_position());
            member.setCard_worker_department(workerDto.getCard_worker_department());
        }

        MemberFanDto fanDto = memberRequest.getMemberFan();
        if (fanDto != null) {
            member.setCard_fan_genre(fanDto.getCard_fan_genre());
            member.setCard_fan_first(fanDto.getCard_fan_first());
            member.setCard_fan_second(fanDto.getCard_fan_second());
            member.setCard_fan_reason(fanDto.getCard_fan_reason());
        }
    }

    // 특정 팀스페이스의 모든 멤버 카드 조회
    @Transactional
    public List<MemberResponse> getAllMembers(long teamId) {
        return memberRepository.findByTeamId(teamId).stream()
                .map(MemberResponse::new).collect(Collectors.toList());
    }

    // 특정 팀스페이스의 특정 멤버 카드 조회
    @Transactional
    public List<MemberResponse> getMember(long teamId, long userId) {
        return memberRepository.findByTeamIdAndUserId(teamId, userId).stream()
                .map(MemberResponse::new).collect(Collectors.toList());
    }
}
