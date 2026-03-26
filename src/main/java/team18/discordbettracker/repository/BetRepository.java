package team18.discordbettracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team18.discordbettracker.model.Bet;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
