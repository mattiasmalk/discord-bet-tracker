package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.formatter.BetHistoryFormatter;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.service.BetService;

import static team18.discordbettracker.util.BotHelpers.getIdFromGuild;

@AllArgsConstructor
@NullMarked
@Component
public class HistoryCommand implements SlashCommand {
	private final BetService betService;
	private final BetHistoryFormatter betHistoryFormatter;

	@Override
	public String getName() {
		return "history";
	}

	@Override
	public String getDescription() {
		return "Get history of last bets.";
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		var user = event.getUser();
		var guildId = getIdFromGuild(event.getGuild());
		var userId = new UserId(user.getIdLong(), guildId);

		var bets = betService.getHistory(userId);
		String message = betHistoryFormatter.format(bets);

		event.reply(message).queue();
	}
}
