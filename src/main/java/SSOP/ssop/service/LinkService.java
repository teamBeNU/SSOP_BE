package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.domain.link.Link;
import SSOP.ssop.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public Link createLink(String baseUrl, User user) {
        Link link = new Link();
        link.setUrl(baseUrl + "/" + generateUniqueIdentifier());
        link.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // 10분 동안 유효
        link.setUser(user);
        return linkRepository.save(link);
    }

    private String generateUniqueIdentifier() {
        // 고유한 링크 식별자 생성 로직
        return Long.toHexString(System.currentTimeMillis());
    }
}
