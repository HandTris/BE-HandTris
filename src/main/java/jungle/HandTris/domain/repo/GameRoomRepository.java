package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.GameRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRoomRepository extends Repository<GameRoom, Long> {

    @Query("SELECT g FROM GameRoom g WHERE g.participantCount < g.participantLimit AND g.gameStatus != 'PLAYING' ")
    List<GameRoom> findAllByGameStatusNotPlaying();

    Optional<GameRoom> findById(Long id);

    GameRoom save(GameRoom gameRoom);

    void delete(GameRoom gameRoom);

    Optional<GameRoom> findByRoomNumber(UUID gameUuid);

}
