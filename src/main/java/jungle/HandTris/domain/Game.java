package jungle.HandTris.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String gameCategory;

    @Column(nullable = false)
    private long participantCount;

    @Column(nullable = false)
    private long participant_limit;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameStatus gameStatus;
}
