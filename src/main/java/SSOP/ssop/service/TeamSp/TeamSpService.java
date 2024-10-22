package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.TeamSp.TeamSpInfoDto;
import SSOP.ssop.repository.TeamSp.MemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpMemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class TeamSpService {

    @Autowired
    private TeamSpRepository teamSpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamSpMemberRepository teamSpMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    // 팀스페이스 생성
    @Transactional
    public ResponseEntity<?> saveTeamSp(TeamSp teamSp, Long hostId) {
        teamSp.setHostId(hostId); // 호스트 ID 저장
        teamSp.setInviteCode(createInviteCode()); // 초대코드 자동 생성
        TeamSp savedTeamSp = teamSpRepository.save(teamSp);

        // 팀스페이스 생성 후 호스트를 자동으로 팀스페이스에 입장시킴
        EnterTeamSp(savedTeamSp.getInviteCode(), hostId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("inviteCode", savedTeamSp.getInviteCode()));
    }

    // 랜덤 코드 생성
    private int createInviteCode() {
        Random random = new Random();
        int inviteCode;
        List<Integer> existingCodes = teamSpRepository.findAllInviteCodes();
        do {
            inviteCode = 100000 + random.nextInt(900000);
        } while (existingCodes.contains(inviteCode));
        return inviteCode;
    }

    // 초대코드 검색
    public TeamSpInfoDto SearchInviteCode(int inviteCode, Long userId) {
        TeamSp teamSp = teamSpRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대 코드입니다."));

        long memberCount = teamSpMemberRepository.countByTeamIdAndCardIdNotNull(teamSp.getTeamId());

        return new TeamSpInfoDto(teamSp, memberCount);
    }

    // 팀스페이스 입장
    @Transactional
    public TeamSpInfoDto EnterTeamSp(int inviteCode, Long userId) {
        // 1. 팀스페이스 정보 가져오기
        TeamSp teamSp = teamSpRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 초대 코드입니다."));

        // 2. 유저 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 3. 이미 입장한 사용자인지 종복 확인
        Optional<TeamSpMember> existingMembership = teamSpMemberRepository.findByTeamSpIdAndUserId(teamSp.getTeamId(), userId);
        if (existingMembership.isPresent()) {
            throw new IllegalArgumentException("이미 입장한 팀스페이스입니다.");
        }

        // 4. 유저 정보에서 팀스페이스 추가
        user.enterTeamSp(teamSp);

        // 5. 팀스페이스 멤버 저장
        teamSpMemberRepository.saveAll(user.getTeamSpMembers());

        // +) 팀별 참여 인원
        long memberCount = teamSpMemberRepository.countByTeamIdAndCardIdNotNull(teamSp.getTeamId());

        // 6. TeamSpInfoDto 결과 반환
        return new TeamSpInfoDto(teamSp, memberCount);
    }

    // 모든 팀스페이스 Entity 조회
    public List<TeamSp> getAllTeams() {
        return teamSpRepository.findAll();
    }

    // 특정 id 팀스페이스 조회
    public TeamSp getTeamById(Long teamId) {
        return teamSpRepository.findById(teamId).orElse(null);
    }

    // 팀스페이스 이름 수정
    @Transactional
    public TeamSp updateTeamSp(Long id, TeamSp teamSp, Long userId) {
        TeamSp existingTeamSp = teamSpRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("팀스페이스를 찾을 수 없습니다."));

        if (existingTeamSp.getHostId() == userId) {
            existingTeamSp.updateTeamName(teamSp.getTeam_name());
            return teamSpRepository.save(existingTeamSp);
        } else {
            throw new RuntimeException("호스트만 팀스페이스 이름을 수정할 수 있습니다.");
        }
    }

    // 팀스페이스 삭제(호스트), 퇴장(참여자)
    @Transactional
    public void deleteTeamSp(Long teamId, Long userId) {
        TeamSp teamSp = getTeamById(teamId);
        if (teamSp == null) {
            throw new IllegalArgumentException("팀스페이스를 찾을 수 없습니다.");
        }

        if (teamSp.getHostId() == userId) {
            // teamSpMember id를 가지고 member에 접근
            List<Member> members = memberRepository.findByTeamId(teamId);

            // member의 profile_image_url 지우기
            for (Member member : members) {
                // 현재 사용자의 이미지 삭제 (AWS S3 파일 삭제)
                String imageUrl = member.getProfile_image_url();      // member의 profile_image_url 가져오기

                // memberService의 deleteImage 메서드 호출
                try {
                    memberService.deleteImage(imageUrl);
                } catch (URISyntaxException e) {
                    throw new RuntimeException("이미지 삭제 중 오류 발생: " + e.getMessage());
                }
            }

            teamSpRepository.delete(teamSp);
        } else {
            // 참여자 처리
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
            TeamSpMember currentUserMember = teamSpMemberRepository.findByTeamSpAndUser(teamSp, user)
                    .orElseThrow(() -> new IllegalArgumentException("현재 사용자가 팀스페이스의 참여자가 아닙니다."));

            // 현재 사용자의 이미지 삭제 (AWS S3 파일 삭제)
            Member mem = memberRepository.findByTeamSpMemberId(currentUserMember.getId())
                    .orElseThrow(() -> new IllegalArgumentException("현재 사용자의 프로필 이미지를 찾을 수 없습니다."));
            String imageUrl = mem.getProfile_image_url();      // member의 profile_image_url 가져오기

            // memberService의 deleteImage 메서드 호출
            try {
                memberService.deleteImage(imageUrl);
            } catch (URISyntaxException e) {
                throw new RuntimeException("이미지 삭제 중 오류 발생: " + e.getMessage());
            }

            // 현재 사용자를 팀스페이스에서 제거
            teamSp.removeMember(currentUserMember);

            // 팀스페이스에서 다른 사용자 제거
            List<TeamSpMember> membersToRemove = teamSpMemberRepository.findByTeamSpAndUserId(teamSp, userId);
            for (TeamSpMember member : membersToRemove) {
                teamSp.removeMember(member);
            }

            teamSpRepository.save(teamSp);
        }
    }
}
