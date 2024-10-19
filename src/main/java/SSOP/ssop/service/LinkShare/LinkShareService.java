package SSOP.ssop.service.LinkShare;

import SSOP.ssop.domain.card.Card;
import SSOP.ssop.domain.card.CardFan;
import SSOP.ssop.domain.card.CardStudent;
import SSOP.ssop.domain.card.CardWorker;
import SSOP.ssop.domain.link.LinkShare;
import SSOP.ssop.dto.card.response.CardResponse;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.repository.LinkShare.LinkShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LinkShareService {

    private final CardRepository cardRepository;
    private final LinkShareRepository linkShareRepository;

    @Autowired
    public LinkShareService(CardRepository cardRepository, LinkShareRepository linkShareRepository) {
        this.cardRepository = cardRepository;
        this.linkShareRepository = linkShareRepository;
    }

    public String createShareLink(Long cardId, Long userId) {
        // 카드 정보 조회
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

        if (!card.getUserId().equals(userId)) {
            throw new IllegalArgumentException("본인의 카드만 공유할 수 있습니다.");
        }

        // 링크 토큰 생성
        String token = UUID.randomUUID().toString();

        // 만료 시간 설정 (현재 시간 + 10분)
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(10);

        // 링크 저장 (DB에)
        LinkShare link = new LinkShare(token, cardId, expiryTime);
        linkShareRepository.save(link);

        // 생성된 링크 반환
        return "https://ssop.com/api/link/" + token;
    }

    // 링크 토큰 카드 정보
    public CardResponse getCardInfoFromToken(String token) {
        // 링크 토큰으로 ShareLink 조회
        LinkShare link = linkShareRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 링크입니다."));

        // 만료 시간 확인
        if (link.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("링크가 만료되었습니다.");
        }

        // 카드 정보 조회
        Card card = cardRepository.findById(link.getCardId())
                .orElseThrow(() -> new IllegalArgumentException("카드를 찾을 수 없습니다."));

        // 카드에 관련된 추가 정보 (CardStudent, CardWorker, CardFan) 조회
        CardStudent cardStudent = cardRepository.findCardStudentByCardId(card.getCardId());
        CardWorker cardWorker = cardRepository.findCardWorkerByCardId(card.getCardId());
        CardFan cardFan = cardRepository.findCardFanByCardId(card.getCardId());
        LocalDateTime createdAt = card.getCreatedAt();  // 카드 생성 시간

        // 현재 로그인된 사용자 ID와 카드 소유자 ID 비교
        boolean isNotMyCard = !card.getUserId().equals(card.getUserId());

        // CardResponse 객체 생성 및 반환
        return new CardResponse(card, cardStudent, cardWorker, cardFan, isNotMyCard, createdAt, null);
    }
}
