package team18.discordbettracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.BetStatus;
import team18.discordbettracker.model.UserId;

import java.util.List;
import java.util.Optional;

public interface BetRepository extends JpaRepository<Bet, Long>, BetRepositoryCustom {
    List<Bet> findLatestByUserId(UserId userId);
    Optional<Bet> findByIdAndUserUserId(Long id, UserId userId);
    List<Bet> findByUserUserIdServerIdAndStatus(Long serverId, BetStatus status);
    List<Bet> findByUserUserIdAndStatus(UserId userId, BetStatus status);
}
