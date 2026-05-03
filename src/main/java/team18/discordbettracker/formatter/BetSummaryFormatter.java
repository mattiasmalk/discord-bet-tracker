package team18.discordbettracker.formatter;

import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.model.BettingSummaryDto;

import java.math.BigDecimal;

@Component
@NullMarked
public class BetSummaryFormatter {

    public String format(BettingSummaryDto summary) {
    StringBuilder message = new StringBuilder("```\n");
    message.append("Betting summary:\n");
    message.append("Open Bets: ").append(summary.openBetsCount()).append("\n");
    message.append("Total Bets: ").append(summary.totalBets()).append("\n");
    message.append("Win Rate: ").append(String.format("%.2f", summary.winRate())).append("%\n");
    message.append("Total Profit: ").append(String.format("%.2f", summary.totalProfit())).append("\n");
    message.append("ROI: ").append(String.format("%.2f", summary.roi())).append("%\n");
    message.append("```");

    return message.toString();
    }
}
