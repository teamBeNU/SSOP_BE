package SSOP.ssop.service;

import SSOP.ssop.domain.link.LinkShare;
import SSOP.ssop.repository.LinkShareRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LinkShareService {

    private final LinkShareRepository linkShareRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public LinkShareService(LinkShareRepository linkShareRepository) {
        this.linkShareRepository = linkShareRepository;
    }

    public LinkShare createLink(Long cardId, int expiryTimeInSeconds) {
        // UUID를 사용하여 랜덤 링크 생성
        String uniqueId = UUID.randomUUID().toString();
        String link = baseUrl + "/share/" + uniqueId;

        // 만료 시간 설정
        LocalDateTime expiryTime = LocalDateTime.now().plusSeconds(expiryTimeInSeconds);

        // LinkShare 엔티티 생성 및 저장
        LinkShare linkShare = new LinkShare(cardId, link, expiryTime);
        return linkShareRepository.save(linkShare);
    }
}
