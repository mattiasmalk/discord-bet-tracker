package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.formatter.BetSummaryFormatter;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.service.BetService;

import static team18.discordbettracker.util.BotHelpers.getIdFromGuild;

@AllArgsConstructor
@NullMarked
@Component
public class StatsCommand implements SlashCommand {
	private final BetService betService;
	private final BetSummaryFormatter betSummaryFormatter;

	@Override
	public String getName() {
		return "stats";
	}

	@Override
	public String getDescription() {
		return "Get your betting summary with stats like win rate, ROI, and total profit.";
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		var user = event.getUser();
		var guildId = getIdFromGuild(event.getGuild());
		var userId = new UserId(user.getIdLong(), guildId);

		var summary = betService.getBettingSummary(userId);
		String message = betSummaryFormatter.format(summary);

		event.reply(message).queue();
	}
}
