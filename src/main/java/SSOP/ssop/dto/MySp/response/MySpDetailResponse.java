package SSOP.ssop.dto.MySp.response;

import SSOP.ssop.dto.card.response.CardResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MySpDetailResponse {
    private Long groupId;
    private String group_name;
    private int memberCount;
    private LocalDateTime createdAt;
    private List<CardResponse> members;  // 멤버 카드 목록

    public MySpDetailResponse(Long groupId, String group_name, int memberCount, LocalDateTime createdAt, List<CardResponse> members) {
        this.groupId = groupId;
        this.group_name = group_name;
        this.memberCount = memberCount;
        this.createdAt = createdAt;
        this.members = members;
    }
}
