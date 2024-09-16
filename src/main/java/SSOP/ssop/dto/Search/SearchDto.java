package SSOP.ssop.dto.Search;

import SSOP.ssop.dto.MySp.response.MySpGroupResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDto {
    private List<CardSearchDto> cardSearchDto;
    private List<MemberSearchDto> memberSearchDto;
    private List<MySpGroupResponse> mySpgroupResponse;

    public SearchDto(List<CardSearchDto> cardSearchDto, List<MemberSearchDto> memberSearchDto, List<MySpGroupResponse> mySpGroupResponse) {
        this.cardSearchDto = cardSearchDto;
        this.memberSearchDto = memberSearchDto;
        this.mySpgroupResponse = mySpGroupResponse;
    }
}
