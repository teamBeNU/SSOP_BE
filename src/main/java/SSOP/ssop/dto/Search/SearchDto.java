package SSOP.ssop.dto.Search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchDto {
    private List<CardSearchDto> savedCardSearchDto = new ArrayList<>(); // 저장한 카드 - 마이스페이스
    private List<Object> teamSpSearchDto = new ArrayList<>(); // 팀스페이스 카드 및 멤버 정보

    // 추가적으로 팀스페이스 카드와 멤버를 묶는 DTO 클래스 필요
//    @Getter
//    @Setter
//    public static class TeamSpSearchDto {
//        private List<CardSearchDto> cardSearchDto = new ArrayList<>();
//        private List<MemberSearchDto> memberSearchDto = new ArrayList<>();
//    }
}