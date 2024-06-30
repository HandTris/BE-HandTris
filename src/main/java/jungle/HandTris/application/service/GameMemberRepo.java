package jungle.HandTris.application.service;

import jungle.HandTris.domain.GameMember;
import org.springframework.data.repository.CrudRepository;

public interface GameMemberRepo extends CrudRepository<GameMember, String> {
}