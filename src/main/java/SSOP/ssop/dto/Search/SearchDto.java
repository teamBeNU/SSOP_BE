package SSOP.ssop.dto.Search;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchDto {
    private List<CardSearchDto> savedCardSearchDto = new ArrayList<>(); // 저장한 카드 - 마이스페이스
    private List<CardSearchDto> cardSearchDto = new ArrayList<>(); // 기존 카드 제출 - 팀스페이스
    private List<MemberSearchDto> memberSearchDto = new ArrayList<>(); // 호스트 지정 카드 제출 - 팀스페이스
}