package SSOP.ssop.service.MySp;

import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.User;
import SSOP.ssop.dto.MySp.request.MySpGroupCreateRequest;
import SSOP.ssop.dto.MySp.response.MySpGroupResponse;
import SSOP.ssop.repository.MySp.MySpRepository;
import SSOP.ssop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 마이스페이스 그룹 목록 조회
    public List<MySpGroupResponse> getMyspGroup(Long userId) {
        // 1. 사용자가 속한 그룹들 조회
        List<MySp> mySpGroups = mySpRepository.findByUserId(userId);

        // 2. 해당 사용자가 속한 그룹이 없다면 예외 처리
        if (mySpGroups.isEmpty()) {
            throw new IllegalArgumentException(userId + " 번 user id가 존재하지 않습니다.");
        }

        // 3. 각 그룹의 멤버 수를 계산하고, 그룹 정보를 응답 객체로 변환
        List<MySpGroupResponse> groupResponses = mySpGroups.stream()
                .map(mySpGroup -> new MySpGroupResponse(
                        mySpGroup.getGroup_id(),
                        mySpGroup.getGroup_name(),
                        // 해당 그룹의 멤버 수 계산 (코드 작성 예정)
                        mySpGroup.getCreatedAt()
                ))
                .collect(Collectors.toList());

        return groupResponses;
    }

    // 마이스페이스 그룹 삭제
    public boolean deleteMyspGroup(Long userId, Long groupId) {
        // 그룹을 찾고, 사용자와 그룹의 연관성을 확인
        Optional<MySp> groupOptional = mySpRepository.findByGroupIdAndUserId(groupId, userId);

        if (groupOptional.isPresent()) {
            mySpRepository.delete(groupOptional.get());  // 그룹 삭제
            return true;  // 삭제 성공
        } else {
            return false; // 그룹을 찾지 못했거나 권한이 없는 경우
        }
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
}
