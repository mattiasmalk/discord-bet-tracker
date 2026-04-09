package team18.discordbettracker.service;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import team18.discordbettracker.model.*;
import team18.discordbettracker.repository.BetRepository;
import team18.discordbettracker.util.BotHelpers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NullMarked
@Service
public class BetService {
    private final BetRepository betRepository;
    private final BetDtoMapper betDtoMapper;

    public BetDto placeBet(User user, String description, BigDecimal stake, BigDecimal odds) {
        var bet = Bet.builder()
                .user(user)
                .description(description)
                .stake(stake)
                .odds(odds)
                .status(BetStatus.OPEN)
                .createdAt(Instant.now())
                .build();

        return betDtoMapper.toDto(betRepository.save(bet));
    }

    public BetDto settleBet(UserId userId, Long betId, BetStatus result) {
        var bet = betRepository.findByIdAndUserUserId(betId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Bet not found or does not belong to you."));

        if (bet.getStatus() != BetStatus.OPEN) {
            throw new IllegalStateException("Bet is already settled.");
        }

        bet.setStatus(result);
        bet.setResolvedAt(Instant.now());

        return betDtoMapper.toDto(betRepository.save(bet));
    }

    public List<BetDto> getHistory(UserId userId) {
        var bets = betRepository.findLatestByUserId(userId);
        var betDtos = betDtoMapper.toDtoList(bets);

        return betDtos;
    }

    public BettingSummaryDto getBettingSummary(UserId userId) {
        var bets = betRepository.findAllByUserId(userId);

        int openBetsCount = (int) bets.stream()
                .filter(bet -> bet.getStatus() == BetStatus.OPEN)
                .count();

        int totalBets = bets.size();

        long wonBetsCount = bets.stream()
                .filter(bet -> bet.getStatus() == BetStatus.WON)
                .count();

        BigDecimal winRate = totalBets == 0
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(wonBetsCount)
                .divide(BigDecimal.valueOf(totalBets), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        BigDecimal totalProfit = bets.stream()
                .map(BotHelpers::calculateProfit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalStake = bets.stream()
                .filter(bet -> bet.getStatus() != BetStatus.OPEN)
                .map(Bet::getStake)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal roi = totalStake.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : totalProfit
                .divide(totalStake, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        return new BettingSummaryDto(openBetsCount, totalBets, winRate, totalProfit, roi);
    }
}
