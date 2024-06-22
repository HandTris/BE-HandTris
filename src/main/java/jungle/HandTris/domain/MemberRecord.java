package jungle.HandTris.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Entity
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

}
