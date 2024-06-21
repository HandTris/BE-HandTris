package jungle.HandTris.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.global.validation.ValidEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameDetailReq {
    @ValidEnum(enumClass = GameCategory.class, message = "존재하지 않는 게임 카테고리입니다.")
    private String gameCategory;
    @Min(1)
    private long participantLimit;
}
