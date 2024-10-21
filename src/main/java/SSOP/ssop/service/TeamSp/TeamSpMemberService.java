package SSOP.ssop.service.TeamSp;

import SSOP.ssop.domain.TeamSp.Member;
import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.TeamSp.*;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.repository.TeamSp.MemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpMemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpRepository;
import SSOP.ssop.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Transactional
    public void SubmitCard(Long teamId, Long cardId, Long userId) {
        // 1. 팀스페이스 멤버 조회
        TeamSpMember teamSpMember = teamSpMemberRepository.findByTeamSpIdAndUserId(teamId, userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀스페이스 멤버입니다."));

        // 2. 유저 카드 목록에서 제출할 카드가 있는지 확인
        List<CardResponse> myCards = cardService.getMyCards(userId);
        boolean isCardOwnedByUser = myCards.stream()
                .anyMatch(card -> card.getCardId().equals(cardId));
        if (!isCardOwnedByUser) {
            throw new IllegalArgumentException("사용자의 카드 목록에 카드가 존재하지 않습니다.");
        }

        // 3. 이미 제출된 카드가 있는지 확인
        if (teamSpMember.getCardId() != null) {
            throw new IllegalArgumentException("이미 제출한 카드가 있습니다. 한 명의 사용자는 하나의 카드만 제출할 수 있습니다.");
        }
        // 4. 카드 제출을 위해 새로운 TeamSpMember 엔티티를 생성
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));

        // 기존 teamSpMember 객체에 카드 ID와 생성 시점 업데이트
        teamSpMember.setCardId(card.getCardId());
        teamSpMember.setCreatedAt(LocalDateTime.now()); // 필요하다면 갱신 시간으로 설정
        teamSpMemberRepository.save(teamSpMember);
    }

    // 팀스페이스 참여 정보 조회
    public List<TeamSpMemberDto> getTeamMembers() {
        // 모든 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findAll();

        // 팀 ID와 사용자 ID 목록으로 그룹화
        Map<Long, List<Long>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(
                        member -> member.getTeamSp().getTeamId(),
                        Collectors.mapping(
                                member -> member.getUser().getUserId(),
                                Collectors.toList()
                        )
                ));

        // 2. 팀스페이스 ID 목록으로 팀 이름과 팀 설명을 가져오는 맵
        Map<Long, TeamSp> teamSpMap = teamSpRepository.findAll().stream()
                .collect(Collectors.toMap(
                        TeamSp::getTeamId,
                        teamSp -> teamSp // 팀 객체 자체를 맵에 저장
                ));

        // 3. 팀 ID와 필터 정보를 가져오기 위한 Map
        Map<Long, FilterDto> filtersMap = new HashMap<>();
        for (Long teamId : teamMembersMap.keySet()) {
            // 해당 팀의 모든 TeamSpMember 객체 가져오기
            List<TeamSpMember> teamSpMembers = members.stream()
                    .filter(member -> member.getTeamSp().getTeamId().equals(teamId))
                    .collect(Collectors.toList());

            // 각 TeamSpMember 객체에서 Member 리스트를 가져와서 필터링합니다
            List<Member> teamMembers = teamSpMembers.stream()
                    .flatMap(teamSpMember -> teamSpMember.getMembers().stream())
                    .collect(Collectors.toList());

            // 필터 정보 생성
            List<String> mbtiList = teamMembers.stream()
                    .map(Member::getCard_MBTI)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> majorList = teamMembers.stream()
                    .map(Member::getCard_student_major)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> roleList = teamMembers.stream()
                    .map(Member::getCard_student_role)
                    .distinct()
                    .collect(Collectors.toList());

            List<String> templateList = teamMembers.stream()
                    .map(Member::getCard_template)
                    .distinct()
                    .collect(Collectors.toList());

            filtersMap.put(teamId, new FilterDto(
                    null, // 카드 ID는 여기에 필요하지 않으므로 null로 설정
                    filterNull(mbtiList),
                    filterNull(majorList),
                    filterNull(roleList),
                    filterNull(templateList)
            ));
        }
            
        // 4. 팀 ID와 사용자 ID 목록으로 TeamSpMemberDto 생성
        return teamMembersMap.entrySet().stream()
                .map(entry -> {
                    TeamSp teamSp = teamSpMap.get(entry.getKey());
                    FilterDto filterDto = filtersMap.get(entry.getKey()); // 필터 정보 추가

                    // 팀에 포함된 모든 사용자에 대해 카드 ID 목록을 가져옵니다
                    List<Long> cardIds = members.stream()
                            .filter(member -> member.getTeamSp().getTeamId().equals(entry.getKey()))
                            .map(member -> member.getCardId() != null ? member.getCardId() : null)
                            .filter(Objects::nonNull) // 카드 ID가 null이 아닌 경우만 필터링
                            .distinct()  // 중복된 카드 ID를 제거
                            .collect(Collectors.toList());

                    // team id를 통해 MemberResponse 객체를 가져옴
                    List<MemberResponse> membersDetail = memberRepository.findByTeamId(entry.getKey()).stream()
                            .map(MemberResponse::new).collect(Collectors.toList());

                    return new TeamSpMemberDto(
                            Long.valueOf(entry.getKey()),  // 팀 ID를 문자열로 변환
                            teamSp.getHostId(),              // 호스트 ID
                            teamSp.getTeam_name(),           // 팀 이름
                            teamSp.getTeam_comment(),        // 팀 설명
                            filterDto,
                            entry.getValue(),                // 사용자 ID 목록
                            cardIds,                         // 카드 ID 목록 (null 제외)
                            membersDetail                    // 멤버 카드 정보
                    );
                })
                .filter(dto -> !dto.getCardIds().isEmpty()) // 카드 ID 목록이 비어있지 않은 경우만 반환
                .collect(Collectors.toList());
    }

    // 특정 id 팀스페이스 참여 정보 조회
    public Optional<TeamSpMemberDto> getTeamMemberById(Long teamId) {
        // 팀스페이스의 멤버 정보를 조회
        List<TeamSpMember> members = teamSpMemberRepository.findByTeamSpId(teamId);

        // 팀스페이스 정보를 조회
        TeamSp teamSp = teamSpRepository.findById(teamId)
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
                .map(member -> member.getCardId() != null ? member.getCardId() : null)
                .filter(Objects::nonNull) // 카드 ID가 null이 아닌 경우만 필터링
                .distinct()  // 중복된 카드 ID를 제거
                .collect(Collectors.toList());

        // 팀스페이스 ID로 필터 정보를 생성
        List<Member> teamMembers = members.stream()
                .flatMap(teamSpMember -> teamSpMember.getMembers().stream())
                .collect(Collectors.toList());

        List<String> mbtiList = teamMembers.stream()
                .map(Member::getCard_MBTI)
                .distinct()
                .collect(Collectors.toList());

        List<String> majorList = teamMembers.stream()
                .map(Member::getCard_student_major)
                .distinct()
                .collect(Collectors.toList());

        List<String> roleList = teamMembers.stream()
                .map(Member::getCard_student_role)
                .distinct()
                .collect(Collectors.toList());

        List<String> templateList = teamMembers.stream()
                .map(Member::getCard_template)
                .distinct()
                .collect(Collectors.toList());

        FilterDto filterDto = new FilterDto(
                null, // 카드 ID는 여기에 필요하지 않으므로 null로 설정
                filterNull(mbtiList),
                filterNull(majorList),
                filterNull(roleList),
                filterNull(templateList)
        );

        // team id를 통해 MemberResponse 객체를 가져옴
        List<MemberResponse> membersDetail = memberRepository.findByTeamId(teamId).stream()
                .map(MemberResponse::new).collect(Collectors.toList());

        // DTO 객체 생성
        TeamSpMemberDto teamSpMemberDto = new TeamSpMemberDto(
                Long.valueOf(teamId),
                teamSp.getHostId(),    // 호스트 ID
                teamSp.getTeam_name(), // 팀 이름
                teamSp.getTeam_comment(), // 팀 설명
                filterDto,
                userIds.isEmpty() ? Collections.emptyList() : userIds, // 사용자 ID 목록
                cardIds, // 카드 ID 목록 (null 제외)
                membersDetail       // 멤버 카드 정보
        );
        return Optional.of(teamSpMemberDto);
    }

    private List<String> filterNull(List<String> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
    }
}