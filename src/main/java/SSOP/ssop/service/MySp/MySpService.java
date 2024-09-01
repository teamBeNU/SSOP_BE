package SSOP.ssop.service.MySp;

import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.MySp.request.MySpGroupCreateRequest;
import SSOP.ssop.repository.MySp.MySpRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MySpService {

    @Autowired
    private MySpRepository mySpRepository;

    @Autowired
    private UserRepository userRepository;

    public MySpService(MySpRepository mySpRepository, UserRepository userRepository) {
        this.mySpRepository = mySpRepository;
        this.userRepository = userRepository;
    }

    // 마이스페이스 그룹 생성
    public void createMyspGroup(Long userId, MySpGroupCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID 없음: " + userId));

        MySp mySp = new MySp(user, request.getGroup_name());
        mySpRepository.save(mySp);
    }
}
