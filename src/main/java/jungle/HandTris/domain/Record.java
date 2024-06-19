package jungle.HandTris.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "nickname", referencedColumnName = "nickname")
    private Member member;

    private long victory;

    private long lose;

    private BigDecimal rate;

    private LocalTime avgTime;

}
