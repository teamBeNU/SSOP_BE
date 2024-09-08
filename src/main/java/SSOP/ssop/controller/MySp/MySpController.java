package SSOP.ssop.controller.MySp;

import SSOP.ssop.domain.MySp.MySp;
import SSOP.ssop.domain.card.Card;
import SSOP.ssop.dto.MySp.request.MySpGroupCreateRequest;
import SSOP.ssop.dto.MySp.response.MySpDetailResponse;
import SSOP.ssop.dto.MySp.response.MySpGroupResponse;
import SSOP.ssop.repository.Card.CardRepository;
import SSOP.ssop.repository.MySp.MySpRepository;
import SSOP.ssop.security.annotation.Login;
import SSOP.ssop.service.MySp.MySpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/mysp")
public class MySpController {

    private final MySpService mySpService;

    @Autowired
    public MySpController(MySpService mySpService) {
        this.mySpService = mySpService;
    }

    // 마이스페이스 그룹 생성
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createGroup(@Login Long userId,@RequestBody MySpGroupCreateRequest request) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("code", 401, "message", "유효한 토큰이 없습니다."));
            }
            mySpService.createMyspGroup(userId, request);
            return ResponseEntity.ok(Map.of("code", 200, "message", "마이스페이스 그룹 생성을 완료했습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("code", 500, "message", "마이스페이스 그룹 생성을 실패했습니다."));
        }
    }

    // 마이스페이스 그룹 목록 조회
    @GetMapping("/view")
    public ResponseEntity<?> getMyspGroup(@Login Long userId) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("code", 401, "message", "유효한 토큰이 없습니다."));
            }
            List<MySpGroupResponse> mySpGroup = mySpService.getMyspGroup(userId);
            return ResponseEntity.ok(mySpGroup);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("code", 500, "message", "그룹 목록 조회에 실패했습니다."));
        }
    }

    // 마이스페이스 그룹 삭제
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> deleteGroup(@Login Long userId, @RequestParam Long groupId) {
        try {
            // 토큰 검증 - 유효하지 않은 경우 401 Unauthorized 반환
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("code", 401, "message", "유효한 토큰이 없습니다."));
            }

            // 그룹 삭제 시도
            boolean isDeleted = mySpService.deleteMyspGroup(userId, groupId);

            // 삭제 성공 여부에 따라 응답 처리
            if (isDeleted) {
                return ResponseEntity.ok(Map.of("message", "그룹이 삭제되었습니다."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "그룹을 삭제하지 못하였습니다."));
            }
        } catch (Exception e) {
            // 예외 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("code", 500, "message", "그룹 삭제에 실패했습니다."));
        }
    }

    // 마이스페이스 그룹명 변경
    @PatchMapping
    public ResponseEntity<?> updateGroupName(@Login Long userId,
                                             @RequestParam Long groupId,
                                             @RequestBody Map<String, String> request) {
        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("code", 401, "message", "유효한 토큰이 없습니다."));
            }

            String newGroupName = request.get("group_name");
            if (newGroupName == null || newGroupName.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("message", "그룹명을 입력해 주세요."));
            }

            MySp updatedGroup = mySpService.updateGroupName(userId, groupId, newGroupName);
            return ResponseEntity.ok(Map.of(
                    "group_id", updatedGroup.getGroupId(),
                    "group_name", updatedGroup.getGroup_name()
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "그룹명 변경에 실패했습니다."));
        }
    }

    // 마이스페이스 그룹 카드 추가
    @PostMapping
    public ResponseEntity<?> addCardsToGroup(
            @Login Long userId,
            @RequestParam Long groupId,
            @RequestBody Map<String, List<Long>> request) {

        try {
            // 1. 토큰 유효성 확인
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("code", 401, "message", "유효한 토큰이 없습니다."));
            }

            // 2. 카드 ID 리스트가 존재하는지 확인
            List<Long> cardIds = request.get("cardId");
            if (cardIds == null || cardIds.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("code", 400, "message", "추가할 카드 ID가 필요합니다."));
            }

            // 3. 서비스 호출하여 카드 추가 처리
            MySpGroupResponse response = mySpService.addCardsToGroup(userId, groupId, cardIds);

            // 4. 성공 응답
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // 5. 잘못된 요청에 대한 예외 처리
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("code", 400, "message", e.getMessage()));
        } catch (Exception e) {
            // 6. 서버 오류에 대한 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("code", 500, "message", "카드를 그룹에 추가하는 중 오류가 발생했습니다."));
        }
    }

    // 그룹별 상세 정보 조회
    @GetMapping
    public ResponseEntity<?> getGroupDetails(
            @RequestParam Long groupId,
            @Login Long userId) {

        try {
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid or missing token"));
            }

            // 그룹 상세 정보 조회
            MySpDetailResponse response = mySpService.getGroupDetails(userId, groupId);

            // 성공적으로 조회된 경우
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            // 그룹 미존재 또는 권한 부족 시 처리
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", e.getMessage()));
        } catch (NoSuchElementException e) {
            // 잘못된 그룹 ID일 경우 처리
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Group not found"));
        } catch (Exception e) {
            // 그 외 예상치 못한 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }
    }
}
