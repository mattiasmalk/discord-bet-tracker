package team18.discordbettracker.service;

import org.springframework.stereotype.Component;
import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.BetDto;
import team18.discordbettracker.model.BetStatus;

import java.math.BigDecimal;
import java.util.List;

@Component
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

    private BigDecimal calculateProfit(Bet bet) {
        var profit = BigDecimal.ZERO;

        if (bet.getStake() == null || bet.getOdds() == null || bet.getStatus() == null) {
            return profit;
        }

        var betStatus = bet.getStatus();
        if (betStatus == BetStatus.WON) {
            profit = bet.getStake().multiply(bet.getOdds().subtract(BigDecimal.ONE));
        } else if (betStatus == BetStatus.LOST) {
            profit = bet.getStake().negate();
        }

        return profit;
    }
}