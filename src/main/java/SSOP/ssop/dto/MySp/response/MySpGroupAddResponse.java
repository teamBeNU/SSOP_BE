package SSOP.ssop.dto.MySp.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MySpGroupAddResponse {
    private Long groupId;
    private String group_name;
    // private int memberCount;
    private LocalDateTime createdAt;

    private int code;
    private String message;
    private List<Long> added_cardIds;  // 추가된 카드 ID 리스트


    // 그룹 정보와 카드 정보 모두 포함하는 생성자
    public MySpGroupAddResponse(Long groupId, String group_name, LocalDateTime createdAt, int code, String message, List<Long> added_cardIds) {
        this.groupId = groupId;
        this.group_name = group_name;
        this.createdAt = createdAt;
        this.code = code;
        this.message = message;
        this.added_cardIds = added_cardIds;
    }
}
