package jungle.HandTris.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private GameCategory gameCategory;

    private long participantCount;
    
    private long participantLimit;

    @Column(nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private GameStatus gameStatus;
}
