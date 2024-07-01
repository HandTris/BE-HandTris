package jungle.HandTris.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "nickname", referencedColumnName = "nickname", nullable = false)
    private Member member;

    private long win;

    private long lose;

    private BigDecimal rate;

    private LocalTime avgTime;

    public MemberRecord(Member member) {
        this.member = member;
        this.win = 0;
        this.lose = 0;
        this.rate = BigDecimal.ZERO;
        this.avgTime = LocalTime.of(0, 0, 0);
    }

    public void win() {
        this.win++;
        updateRate();
    }

    public void lose() {
        this.lose++;
        updateRate();
    }

    private void updateRate() {
        if (this.win + this.lose == 0) {
            this.rate = BigDecimal.ZERO;
        } else {
            this.rate = BigDecimal.valueOf((double) this.win / (this.win + this.lose) * 100)
                    .setScale(2, RoundingMode.HALF_UP);
        }
    }
}
