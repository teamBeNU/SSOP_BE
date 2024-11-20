package SSOP.ssop.service;

import SSOP.ssop.domain.User;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class UserCardUtils {

    private final UserRepository userRepository;

    @Autowired
    public UserCardUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteSavedCard(User user, Long cardId) {
        Map<Long, LocalDateTime> savedCardList = user.getSaved_card_list();

        if (savedCardList.remove(cardId) != null) {
            userRepository.save(user); // 변경 사항 저장
        } else {
            throw new IllegalArgumentException("저장한 카드가 아닙니다. 카드 ID: " + cardId);
        }
    }
}
