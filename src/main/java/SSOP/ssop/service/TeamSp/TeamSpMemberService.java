package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.TeamSp.MemberResponse;
import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.dto.TeamSp.TeamSpMemberDto;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.*;
import SSOP.ssop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamSpMemberService {

    @Autowired
    private TeamSpRepository teamSpRepository;

    @Autowired
    private TeamSpMemberRepository teamSpMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    // 기존 카드 제출
    public void SubmitCard(Long teamId, Long cardId, Long userId) {
        // 1. 팀스페이스 멤버 조회
        TeamSpMember teamSpMember = teamSpMemberRepository.findByTeamSpIdAndUserId(teamId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀스페이스 멤버입니다."));

        // 2. 유저 카드 목록에서 제출할 카드가 있는지 확인
        List<CardResponse> myCards = cardService.getMyCards(userId);
        boolean isCardOwnedByUser = myCards.stream()
                .anyMatch(card -> card.getCard_id().equals(cardId));
        if (!isCardOwnedByUser) {
            throw new IllegalArgumentException("사용자의 카드 목록에 카드가 존재하지 않습니다.");
        }

        // 3. 이미 제출된 카드가 있는지 확인
        Optional<TeamSpMember> existingMembership = teamSpMemberRepository.findByTeamSpIdAndUserId(teamId, userId);

        if (existingMembership.isPresent()) {
            TeamSpMember existingMember = existingMembership.get();

            // 기존 카드가 null -> 요청한 cardId로 업데이트
            if (existingMember.getCard() == null) {
                Card card = cardRepository.findById(cardId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));
                existingMember.setCard(card);
                teamSpMemberRepository.save(existingMember);
                return; // 업데이트 완료 후 메소드 종료
            } else {
                // 카드가 null이 아니면 이미 제출한 카드가 있는 것으로 간주
                throw new IllegalArgumentException("이미 제출한 카드가 있습니다. 한 명의 사용자는 하나의 카드만 제출할 수 있습니다.");
            }
        }
        // 4. 카드 제출을 위해 새로운 TeamSpMember 엔티티를 생성
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));

        // 새로운 TeamSpMember 엔티티를 생성하여 카드 정보를 저장
        TeamSpMember newTeamSpMember = new TeamSpMember(teamSpMember.getTeamSp(), teamSpMember.getUser(), card);
        teamSpMemberRepository.save(newTeamSpMember);
    }


    // 팀스페이스 참여 정보 조회
    public List<TeamSpMemberDto> getTeamMembers() {
        // 모든 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findAll();

        // 팀 ID와 사용자 ID 목록으로 그룹화
        Map<Long, List<Long>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(
                        member -> member.getTeamSp().getTeam_id(),
                        Collectors.mapping(
                                member -> member.getUser().getUserId(),
                                Collectors.toList()
                        )
                ));

        // 2. 팀스페이스 ID 목록으로 팀 이름과 팀 설명을 가져오는 맵
        Map<Long, TeamSp> teamSpMap = teamSpRepository.findAll().stream()
                .collect(Collectors.toMap(
                        TeamSp::getTeam_id,
                        teamSp -> teamSp // 팀 객체 자체를 맵에 저장
                ));

        // 3. 팀 ID와 사용자 ID 목록으로 TeamSpMemberDto 생성
        return teamMembersMap.entrySet().stream()
                .map(entry -> {
                    TeamSp teamSp = teamSpMap.get(entry.getKey());

                    // 팀에 포함된 모든 사용자에 대해 카드 ID 목록을 가져옵니다
                    List<Long> cardIds = members.stream()
                            .filter(member -> member.getTeamSp().getTeam_id().equals(entry.getKey()))
                            .map(member -> member.getCard() != null ? member.getCard().getCard_id() : null)
                            .filter(Objects::nonNull) // 카드 ID가 null이 아닌 경우만 필터링
                            .distinct()  // 중복된 카드 ID를 제거
                            .collect(Collectors.toList());

                    // team id를 통해 MemberResponse 객체를 가져옴
                    List<MemberResponse> membersDetail = memberRepository.findByTeamId(entry.getKey()).stream()
                            .map(MemberResponse::new).collect(Collectors.toList());

                    return new TeamSpMemberDto(
                            String.valueOf(entry.getKey()),  // 팀 ID를 문자열로 변환
                            teamSp.getTeam_name(),           // 팀 이름
                            teamSp.getTeam_comment(),        // 팀 설명
                            entry.getValue(),                // 사용자 ID 목록
                            cardIds,                         // 카드 ID 목록 (null 제외)
                            membersDetail                    // 멤버 카드 정보
                    );
                })
                .filter(dto -> !dto.getCard_id().isEmpty()) // 카드 ID 목록이 비어있지 않은 경우만 반환
                .collect(Collectors.toList());
    }


    // 특정 id 팀스페이스 참여 정보 조회
// 특정 id 팀스페이스 참여 정보 조회
    public Optional<TeamSpMemberDto> getTeamMemberById(Long team_id) {
        // 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findByTeamSpId(team_id);

        // 팀스페이스 정보를 조회
        TeamSp teamSp = teamSpRepository.findById(team_id)
                .orElseThrow(() -> new IllegalArgumentException("팀스페이스를 찾을 수 없습니다."));

        // 멤버 정보가 없으면 빈 Optional 반환
        if (members.isEmpty()) {
            return Optional.empty();
        }

        // 팀스페이스 ID와 사용자 ID 목록으로 변환
        List<Long> userIds = members.stream()
                .map(member -> member.getUser().getUserId())
                .collect(Collectors.toList());

        // 사용자의 카드 목록 조회 및 카드 ID 추출
        List<Long> cardIds = members.stream()
                .map(member -> member.getCard() != null ? member.getCard().getCard_id() : null)
                .filter(Objects::nonNull) // 카드 ID가 null이 아닌 경우만 필터링
                .distinct()  // 중복된 카드 ID를 제거
                .collect(Collectors.toList());

        // team id를 통해 MemberResponse 객체를 가져옴
        List<MemberResponse> membersDetail = memberRepository.findByTeamId(team_id).stream()
                .map(MemberResponse::new).collect(Collectors.toList());

        // DTO 객체 생성
        TeamSpMemberDto teamSpMemberDto = new TeamSpMemberDto(
                String.valueOf(team_id),
                teamSp.getTeam_name(), // 팀 이름
                teamSp.getTeam_comment(), // 팀 설명
                userIds.isEmpty() ? Collections.emptyList() : userIds, // 사용자 ID 목록
                cardIds, // 카드 ID 목록 (null 제외)
                membersDetail       // 멤버 카드 정보
        );
        return Optional.of(teamSpMemberDto);
    }

    // 유저별 참여 중인 팀스페이스 정보 조회
    public List<TeamSpByUserDto> getTeamSpByUserId(Long userId) {
        // 유저가 참여 중인 팀스페이스 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findByUserId(userId);

        // 멤버 정보가 없으면 빈 리스트 반환
        if (members.isEmpty()) {
            return Collections.emptyList();
        }
        // 팀스페이스 ID 목록으로 변환
        Map<Long, List<Long>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(
                        member -> member.getTeamSp().getTeam_id(),
                        Collectors.mapping(
                                member -> member.getUser().getUserId(),
                                Collectors.toList()
                        )
                ));
        // 팀 ID와 팀 이름 및 팀 설명을 조회
        Map<Long, TeamSp> teamSpMap = teamSpRepository.findAll().stream()
                .collect(Collectors.toMap(
                        TeamSp::getTeam_id,
                        teamSp -> teamSp // 팀 객체 자체를 맵에 저장
                ));

        // 팀 ID와 사용자 ID 목록으로 TeamSpMemberDto 생성
        return teamMembersMap.entrySet().stream()
                .map(entry -> {
                    TeamSp teamSp = teamSpMap.get(entry.getKey());

                    // user id를 통해 MemberResponse 객체를 가져옴
                    List<MemberResponse> membersDetail = memberRepository.findByTeamIdAndUserId(entry.getKey(), userId).stream()
                            .map(MemberResponse::new).collect(Collectors.toList());

                    return new TeamSpByUserDto(
                            String.valueOf(entry.getKey()),  // 팀 ID를 문자열로 변환
                            teamSp.getTeam_name(),           // 팀 이름
                            teamSp.getTeam_comment(),       // 팀 설명
                            membersDetail                   // 멤버 카드 정보
                    );
                })
                .collect(Collectors.toList());
    }
}