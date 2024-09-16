package SSOP.ssop.dto.Search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchDto {
    private List<CardSearchDto> cardSearchDto;
    private List<MemberSearchDto> memberSearchDto;

    public SearchDto(List<CardSearchDto> cardSearchDto, List<MemberSearchDto> memberSearchDto) {
        this.cardSearchDto = cardSearchDto;
        this.memberSearchDto = memberSearchDto;
    }
}
