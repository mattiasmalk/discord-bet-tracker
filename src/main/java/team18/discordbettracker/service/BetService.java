package team18.discordbettracker.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team18.discordbettracker.model.BetDto;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.repository.BetRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class BetService {
    private final BetRepository betRepository;
    private final BetDtoMapper betDtoMapper;

    public List<BetDto> getHistory(UserId userId) {
        var bets = betRepository.findLatestByUserId(userId);
        var betDtos = betDtoMapper.toDtoList(bets);

        return betDtos;
    }
}
