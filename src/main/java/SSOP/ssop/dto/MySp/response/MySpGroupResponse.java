package SSOP.ssop.dto.MySp.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MySpGroupResponse {
    private Long group_id;
    private String group_name;
    // private int memberCount;
    private LocalDateTime createdAt;

    public MySpGroupResponse(Long group_id, String group_name, LocalDateTime createdAt) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.createdAt = createdAt;
    }

//    public MySpGroupResponse(Long group_id, String group_name, int memberCount, LocalDateTime createdAt) {
//        this.group_id = group_id;
//        this.group_name = group_name;
//        this.memberCount = memberCount;
//        this.createdAt = createdAt;
//    }
}
