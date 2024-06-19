package jungle.HandTris.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    private long victory;

    private long lose;

    private BigDecimal rate;

    private LocalTime avgTime;

}
