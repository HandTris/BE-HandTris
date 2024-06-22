package jungle.HandTris.presentation.dto.request;

import jakarta.validation.constraints.Min;
import jungle.HandTris.domain.GameCategory;
import jungle.HandTris.global.validation.ValidEnum;

public record GameDetailReq(
        @ValidEnum(enumClass = GameCategory.class, message = "존재하지 않는 게임 카테고리입니다.")
        String gameCategory,
        @Min(1)
        long participantLimit
) {
}
