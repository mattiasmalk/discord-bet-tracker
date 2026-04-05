package team18.discordbettracker.model;

import java.math.BigDecimal;
import java.time.Instant;

public record BetDto(
        Long id,
        String description,
        BigDecimal stake,
        BigDecimal odds,
        BetStatus status,
        BigDecimal profit,
        Instant createdAt,
        Instant resolvedAt
) {

}
