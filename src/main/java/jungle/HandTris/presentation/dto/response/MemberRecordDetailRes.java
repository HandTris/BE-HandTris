package jungle.HandTris.presentation.dto.response;


import jungle.HandTris.domain.MemberRecord;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
public class MemberRecordDetailRes {
    private final MemberDetailRes memberDetailRes;
    private final long win;
    private final long lose;
    private final BigDecimal rate;
    private final LocalTime avgTime;

    public MemberRecordDetailRes(MemberRecord memberRecord) {
        this.win = memberRecord.getWin();
        this.lose = memberRecord.getLose();
        this.rate = memberRecord.getRate();
        this.avgTime = memberRecord.getAvgTime();
        this.memberDetailRes = new MemberDetailRes(memberRecord.getMember());
    }

}
