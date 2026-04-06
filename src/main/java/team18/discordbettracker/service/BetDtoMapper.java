package team18.discordbettracker.service;

import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.BetDto;

import java.util.List;

import static team18.discordbettracker.util.BotHelpers.calculateProfit;

@Component
@NullMarked
public class BetDtoMapper {

    public BetDto toDto(Bet bet) {
        return new BetDto(
                bet.getId(),
                bet.getDescription(),
                bet.getStake(),
                bet.getOdds(),
                bet.getStatus(),
                calculateProfit(bet),
                bet.getCreatedAt(),
                bet.getResolvedAt()
        );
    }

    public List<BetDto> toDtoList(List<Bet> bets) {
        return bets.stream()
                .map(this::toDto)
                .toList();
    }
}