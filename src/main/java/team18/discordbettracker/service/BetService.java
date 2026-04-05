package team18.discordbettracker.service;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import team18.discordbettracker.model.*;
import team18.discordbettracker.repository.BetRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

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

    public List<BetDto> getHistory(UserId userId) {
        var bets = betRepository.findLatestByUserId(userId);
        var betDtos = betDtoMapper.toDtoList(bets);

        return betDtos;
    }

}
