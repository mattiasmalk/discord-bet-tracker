package team18.discordbettracker.formatter;

import org.springframework.stereotype.Component;
import team18.discordbettracker.model.BetDto;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Component
public class BetHistoryFormatter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
                    .withZone(ZoneId.of("Europe/Tallinn"));

    private String formatInstant(java.time.Instant instant) {
        return instant == null ? "-" : DATE_TIME_FORMATTER.format(instant);
    }


    public String format(Collection<BetDto> bets) {
        if (bets.isEmpty()) {return "No bets found.";}

        StringBuilder message = new StringBuilder("```\n");
        message.append(String.format("%-15s %-15s %-8s %-8s %-20s %-8s %-8s\n",
                "CREATED", "RESOLVED", "STAKE", "ODDS", "DESCRIPTION", "STATUS", "PROFIT"));
        message.append("-".repeat(90)).append("\n");

        for (var bet : bets) {
            message.append(String.format("%-15s %-15s %-8s %-8s %-20s %-8s %-8s\n",
                    formatInstant(bet.createdAt()),
                    formatInstant(bet.resolvedAt()),
                    bet.stake(),
                    bet.odds(),
                    bet.description().length() > 20 ? bet.description().substring(0, 17) + "..." : bet.description(),
                    bet.status(),
                    bet.profit()));
        }
        message.append("```");
        return message.toString();
    }
}
