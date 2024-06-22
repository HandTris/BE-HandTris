package jungle.HandTris.domain.repo;

import jungle.HandTris.domain.Game;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends Repository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE g.participantCount < g.participantLimit AND g.gameStatus != 'PLAYING' ")
    List<Game> findAllByGameStatusNotPlaying();

    Optional<Game> findById(Long id);

    Game save(Game game);

    void delete(Game game);
}
