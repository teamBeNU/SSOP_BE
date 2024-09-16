package SSOP.ssop.service;

import SSOP.ssop.domain.TeamSp.TeamSp;
import SSOP.ssop.domain.TeamSp.TeamSpMember;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.Search.CardSearchDto;
import SSOP.ssop.dto.Search.MemberSearchDto;
import SSOP.ssop.dto.Search.SearchDto;
import SSOP.ssop.dto.TeamSp.MemberResponse;
import SSOP.ssop.dto.TeamSp.TeamSpByUserDto;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.repository.TeamSp.MemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpMemberRepository;
import SSOP.ssop.repository.TeamSp.TeamSpRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamSpRepository teamSpRepository;

    @Autowired
    private TeamSpMemberRepository teamSpMemberRepository;

    // 검색 기능 ( 저장한 카드 + 내가 참여중인 팀스페이스 )
    public SearchDto searchByKeyword(Long userId, String keyword) {
        // 유저가 참여 중인 팀스페이스 ID 목록 조회
        List<TeamSpByUserDto> teamSpaces = getTeamSpByUserId(userId);
        List<Long> teamSpIds = teamSpaces.stream()
                .map(TeamSpByUserDto::getTeamId)
                .collect(Collectors.toList());

        // 저장한 카드 ID 목록을 가져옴
        List<Long> savedCardIds = getSavedCardIds(userId);

        System.out.println("Received keyword: " + keyword);
        System.out.println("TeamSpIds: " + teamSpIds);
        System.out.println("SavedCardIds: " + savedCardIds);

        // Card 테이블에서 검색 (저장한 카드 ID를 포함하여 검색)
        List<CardSearchDto> cardSearchDto = cardRepository.searchByKeywordAndSavedCardIds(keyword, savedCardIds);

        // Member 테이블에서 검색
        List<MemberSearchDto> members = memberRepository.searchByKeywordAndTeamSpIds(keyword, teamSpIds);
        List<MemberSearchDto> memberSearchDto = members.stream()
                .map(member -> {
                    MemberSearchDto dto = new MemberSearchDto();
                    dto.setCardId(member.getCardId());
                    dto.setCard_name(member.getCard_name());
                    return dto;
                })
                .collect(Collectors.toList());

        // 통합된 검색 결과
        return new SearchDto(cardSearchDto, memberSearchDto);
    }

    // 저장한 카드 ID 목록을 가져오는 메서드
    public List<Long> getSavedCardIds(Long userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 아이디입니다 : " + userId));
        return new ArrayList<>(user.getSaved_card_list().keySet());
    }


    // 유저가 참여 중인 팀스페이스 멤버 정보를 조회
    public List<TeamSpByUserDto> getTeamSpByUserId(Long userId) {
        List<TeamSpMember> members = teamSpMemberRepository.findByUserId(userId);

        // 멤버 정보가 없으면 빈 리스트 반환
        if (members.isEmpty()) {
            return Collections.emptyList();
        }

        // 팀스페이스 ID를 기준으로 멤버들을 그룹화
        Map<Long, List<TeamSpMember>> teamMembersMap = members.stream()
                .collect(Collectors.groupingBy(
                        member -> member.getTeamSp().getTeamId()
                ));

        // 팀스페이스 ID와 팀 정보를 매핑 (팀 이름, 설명 등)
        Map<Long, TeamSp> teamSpMap = teamSpRepository.findAllById(
                        members.stream()
                                .map(member -> member.getTeamSp().getTeamId())
                                .collect(Collectors.toList())
                ).stream()
                .collect(Collectors.toMap(TeamSp::getTeamId, teamSp -> teamSp));

        // 팀스페이스 ID와 유저별 카드 ID를 매핑
        Map<Long, Long> teamCardIdMap = members.stream()
                .filter(member -> member.getCardId() != null)
                .collect(Collectors.toMap(
                        member -> member.getTeamSp().getTeamId(),
                        member -> member.getCardId(),
                        (existing, replacement) -> existing // 중복되는 경우 기존 값 유지
                ));

        // 팀스페이스와 해당 멤버 정보를 조합하여 TeamSpByUserDto 생성
        return teamSpMap.entrySet().stream()
                .filter(entry -> teamMembersMap.containsKey(entry.getKey())) // 유저가 참여 중인 팀만 필터링
                .map(entry -> {
                    Long teamId = entry.getKey();
                    TeamSp teamSp = entry.getValue();

                    Long cardId = teamCardIdMap.getOrDefault(teamId, null); // 해당 팀의 카드 ID

                    // 해당 팀스페이스에 속한 멤버 정보 조회
                    List<MemberResponse> membersDetail = teamMembersMap.getOrDefault(teamId, Collections.emptyList()).stream()
                            .flatMap(teamSpMember -> memberRepository.findByTeamSpMemberId(teamSpMember.getId()).stream())
                            .map(MemberResponse::new)
                            .collect(Collectors.toList());

                    return new TeamSpByUserDto(
                            teamId,
                            teamSp.getTeam_name(),
                            teamSp.getTeam_comment(),
                            cardId,  // 단일 카드 ID
                            membersDetail // 멤버 리스트
                    );
                })
                .collect(Collectors.toList());
    }
}
