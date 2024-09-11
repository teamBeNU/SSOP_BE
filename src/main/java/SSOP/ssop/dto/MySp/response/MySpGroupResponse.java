package SSOP.ssop.dto.MySp.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MySpGroupResponse {
    private Long groupId;
    private String group_name;
    // private int memberCount;
    private LocalDateTime createdAt;


    public MySpGroupResponse(Long groupId, String group_name, LocalDateTime createdAt) {
        this.groupId = groupId;
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
