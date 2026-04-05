package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.formatter.BetHistoryFormatter;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.service.BetService;

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
	public CommandData getCommandData() {
		return Commands.slash(getName(), getDescription());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		var user = event.getUser();
		var guildId = event.getGuild() != null ? event.getGuild().getIdLong() : 0L;
		var userId = new UserId(user.getIdLong(), guildId);

		var bets = betService.getHistory(userId);
		String message = betHistoryFormatter.format(bets);

		event.reply(message).queue();
	}
}
