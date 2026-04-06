package team18.discordbettracker.util;

import jakarta.annotation.Nullable;
import net.dv8tion.jda.api.entities.Guild;
import team18.discordbettracker.model.Bet;
import team18.discordbettracker.model.BetStatus;

import java.math.BigDecimal;

public class BotHelpers {
	public static long getIdFromGuild(@Nullable Guild guild) {
		return guild == null
				? 0L
				: guild.getIdLong();
	}

	public static BigDecimal calculateProfit(Bet bet) {
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
