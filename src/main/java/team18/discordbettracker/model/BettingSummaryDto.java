package team18.discordbettracker.model;

import java.math.BigDecimal;

public record BettingSummaryDto(
        int openBetsCount,
        int totalBets,
        BigDecimal winRate,
        BigDecimal totalProfit,
        BigDecimal roi
) {
}
