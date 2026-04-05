package team18.discordbettracker.repository;

import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.UserId;

import java.util.List;

public interface BetRepositoryCustom {
    List<Bet> findLatestByUserId(UserId userId);
}
