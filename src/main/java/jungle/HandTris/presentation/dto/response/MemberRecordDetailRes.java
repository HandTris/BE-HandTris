package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.MemberRecord;

import java.math.BigDecimal;
import java.time.LocalTime;

public record MemberRecordDetailRes(
        long win,
        long lose,
        BigDecimal rate,
        LocalTime avgTime
) {
    public MemberRecordDetailRes(MemberRecord memberRecord) {
        this(memberRecord.getWin(),
             memberRecord.getLose(),
             memberRecord.getRate(),
             memberRecord.getAvgTime());
    }
}
