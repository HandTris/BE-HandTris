package jungle.HandTris.presentation.dto.request;

import jungle.HandTris.global.validation.ValidEnum;

public record GameResultReq(
        @ValidEnum(enumClass = GameResult.class, message = "존재하지 않는 결과입니다.")
        String gameResult
) {
}
