package jungle.HandTris.presentation.dto.response;

import jungle.HandTris.domain.MemberRecord;

import java.math.BigDecimal;
import java.time.LocalTime;

public record MemberRecordDetailRes(
        MemberDetailRes memberDetailRes,
        long win,
        long lose,
        BigDecimal rate,
        LocalTime avgTime
) {
    public MemberRecordDetailRes(MemberRecord memberRecord) {
        this(new MemberDetailRes(memberRecord.getMember()),
                memberRecord.getWin(),
                memberRecord.getLose(),
                memberRecord.getRate(),
                memberRecord.getAvgTime());
    }
}
