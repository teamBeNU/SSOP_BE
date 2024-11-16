package SSOP.ssop.service.MySp;

import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.User;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardFan;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.dto.MySp.request.MySpGroupCreateRequest;
import SSOP.ssop.dto.MySp.response.MySpDetailResponse;
import SSOP.ssop.dto.MySp.response.MySpGroupAddResponse;
import SSOP.ssop.dto.MySp.response.MySpGroupResponse;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.repository.MySp.MySpRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MySpService {

    @Autowired
    private MySpRepository mySpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    public MySpService(MySpRepository mySpRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.mySpRepository = mySpRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    // 마이스페이스 그룹 생성
    public void createMyspGroup(Long userId, MySpGroupCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음: " + userId));

        MySp mySp = new MySp(user, request.getGroup_name());
        mySpRepository.save(mySp);
    }

    // 마이스페이스 그룹 목록 조회
    public List<MySpGroupResponse> getMyspGroup(Long userId) {
        // 사용자가 속한 그룹들 조회
        List<MySp> mySpGroups = mySpRepository.findByUserId(userId);

        // 해당 사용자가 속한 그룹이 없다면 예외 처리
        if (mySpGroups.isEmpty()) {
            throw new IllegalArgumentException(userId + " 번 user id가 존재하지 않습니다.");
        }

        // 그룹 정보를 응답 객체로 변환
        List<MySpGroupResponse> groupResponses = mySpGroups.stream()
                .map(mySpGroup -> new MySpGroupResponse(
                        mySpGroup.getGroupId(),         // 그룹 id
                        mySpGroup.getGroup_name(),      // 그룹 이름
                        mySpGroup.getCards().size(),    // 그룹 카드 개수
                        mySpGroup.getCreatedAt()        // 그룹 생성 날짜
                ))
                .collect(Collectors.toList());

        return groupResponses;
    }

    // 마이스페이스 그룹 삭제
    @Transactional
    public boolean deleteMyspGroup(Long userId, Long groupId) {
        // 그룹 존재 확인
        MySp group = mySpRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없거나 권한이 없습니다."));

        // 그룹 내 카드와의 관계 제거
        for (Card card : group.getCards()) {
            card.setMySp(null); // 그룹과 카드의 연관 관계 끊기
            cardRepository.save(card); // 카드 상태 저장

            // 저장된 상대 카드 목록에서도 제거
            deleteSavedCard(userId, List.of(card.getCardId()));
        }

        // 그룹 삭제
        mySpRepository.delete(group);
        return true; // 성공
    }

    // 저장된 상대 카드 목록에서 삭제
    private void deleteSavedCard(Long userId, List<Long> cardIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저 아이디입니다 : " + userId));

        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        for (Long cardId : cardIds) {
            savedCardList.remove(cardId); // 상대 카드 목록에서 삭제
        }

        userRepository.save(user); // 변경 사항 저장
    }

    // 그룹 내 카드 삭제
    @Transactional
    public void removeCardFromGroup(Long userId, Long groupId, Long cardId) {
        // 그룹 확인
        MySp group = mySpRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없거나 권한이 없습니다."));

        // 카드 확인
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

        // 카드가 그룹에 속해 있는지 확인
        if (!group.getCards().contains(card)) {
            throw new IllegalArgumentException("카드가 해당 그룹에 속해 있지 않습니다. 카드 ID: " + cardId);
        }

        // 그룹에서 카드 제거
        group.removeCard(card); // 그룹과의 연관관계 해제
        mySpRepository.save(group); // 그룹 저장

        // 상대 카드 목록에서 제거
        deleteSavedCard(userId, List.of(cardId));
    }

    // 마이스페이스 그룹명 변경
    public MySp updateGroupName(Long userId, Long groupId, String newGroupName) {
        Optional<MySp> groupOptional = mySpRepository.findByGroupIdAndUserId(groupId, userId);

        if (groupOptional.isPresent()) {
            MySp group = groupOptional.get();
            group.setGroup_name(newGroupName);  // 그룹명 변경
            return mySpRepository.save(group);  // 변경사항 저장 및 반환
        } else {
            throw new IllegalArgumentException("해당 그룹을 찾을 수 없습니다.");
        }
    }

    // 마이스페이스 그룹 카드 추가
    public MySpGroupAddResponse addCardsToGroup(Long userId, Long groupId, List<Long> cardIds) {
        // 그룹이 존재하는지 확인
        MySp group = mySpRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없거나 권한이 없습니다."));

        // 추가된 카드들의 ID를 저장할 리스트
        List<Long> addedCardIds = new ArrayList<>();

        // 각 카드에 대해 처리
        for (Long cardId : cardIds) {
            // 카드가 존재하는지 확인
            Card card = cardRepository.findById(cardId)
                    .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

            // 자신의 카드인지 확인 (자신의 카드는 추가 불가)
            if (card.getUserId().equals(userId)) {  // card.getOwnerId() 대신 card.getUserId()로 변경
                throw new IllegalArgumentException("본인의 카드는 추가할 수 없습니다.");
            }

            // 카드가 이미 그룹에 추가되어 있는지 확인
            if (group.getCards().contains(card)) {
                // 예외 발생 또는 메시지 반환
                throw new IllegalArgumentException("이미 추가된 카드입니다: 카드 ID " + cardId);
            }

            // 그룹에 카드 추가
            group.addCard(card);
            addedCardIds.add(card.getCardId());
        }

        // 그룹 정보 저장
        mySpRepository.save(group);

        // 성공 응답 반환
        // 그룹 정보와 추가된 카드 정보를 포함한 응답 반환
        return new MySpGroupAddResponse(
                group.getGroupId(),        // 그룹 ID
                group.getGroup_name(),      // 그룹 이름
                group.getCreatedAt(),       // 그룹 생성 날짜
                200,                        // 응답 코드
                "카드가 그룹에 성공적으로 추가되었습니다.",  // 메시지
                addedCardIds                // 추가된 카드 ID 리스트
        );
    }

    // 그룹별 상세 조회
    public MySpDetailResponse getGroupDetails(Long userId, Long groupId) {
        // 그룹 존재 및 소유 여부 확인
        MySp group = mySpRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new IllegalArgumentException("그룹을 찾을 수 없거나 권한이 없습니다."));

        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 저장된 카드 목록 가져오기 (카드 ID와 저장된 시간 매핑)
        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        // 그룹에 속한 카드 정보 조회
        List<CardResponse> members = group.getCards().stream()
                .map(card -> {
                    // 카드별 상세 정보 가져오기
                    CardStudent cardStudent = cardRepository.findCardStudentByCardId(card.getCardId());
                    CardWorker cardWorker = cardRepository.findCardWorkerByCardId(card.getCardId());
                    CardFan cardFan = cardRepository.findCardFanByCardId(card.getCardId());

                    // 저장 시간 가져오기
                    LocalDateTime savedAt = savedCardList.getOrDefault(card.getCardId(), null);

                    // CardResponse 생성 및 반환
                    return new CardResponse(card, cardStudent, cardWorker, cardFan, true, savedAt, card.getCreatedAt());
                })
                .collect(Collectors.toList());

        // 그룹 상세 정보와 멤버 카드 정보 반환
        return new MySpDetailResponse(
                group.getGroupId(),
                group.getGroup_name(),
                members.size(),
                group.getCreatedAt(),
                members
        );
    }
}
