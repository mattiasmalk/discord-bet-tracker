package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.service.BetService;
import team18.discordbettracker.service.UserService;

import java.math.BigDecimal;
import java.util.List;

import static team18.discordbettracker.util.BotHelpers.getIdFromGuild;

@NullMarked
@Component
@AllArgsConstructor
public class AddBetCommand implements SlashCommand {
	public static final String ADD_BET_OPTION_DESCRIPTION = "description";
	public static final String ADD_BET_OPTION_STAKE = "stake";
	public static final String ADD_BET_OPTION_ODDS = "odds";

	private final BetService betService;
	private final UserService userService;

	@Override
	public String getName() {
		return "bet";
	}

	@Override
	public String getDescription() {
		return "Registers a bet to the command-issuing user with given description, amount and odds.";
	}

	@Override
	public List<OptionData> getRequiredOptions() {
		return List.of(
				new OptionData(OptionType.STRING, ADD_BET_OPTION_DESCRIPTION, "Description of the bet", true),
				new OptionData(OptionType.NUMBER, ADD_BET_OPTION_STAKE, "Money at stake", true),
				new OptionData(OptionType.NUMBER, ADD_BET_OPTION_ODDS, "Odds of the bet", true));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		var discordUser = event.getUser();
		var guildId = getIdFromGuild(event.getGuild());
		var userId = new UserId(discordUser.getIdLong(), guildId);
		var user = userService.findByUserId(userId)
				.orElseGet(() -> userService.createUser(userId, discordUser.getName()));

		var bet = betService.placeBet(user,
				getDescription(event),
				BigDecimal.valueOf(getStake(event)),
				BigDecimal.valueOf(getOdds(event)));

		event.reply("User %s has just placed a bet \"%s\" for %.2f with %.3f odds"
				.formatted(
						user.getName(),
						bet.description(),
						bet.stake(),
						bet.odds())).queue();
	}

	private String getDescription(SlashCommandInteractionEvent event) {
		return event.getOption(ADD_BET_OPTION_DESCRIPTION).getAsString();
	}
	private double getStake(SlashCommandInteractionEvent event) {
		return event.getOption(ADD_BET_OPTION_STAKE).getAsDouble();
	}
	private double getOdds(SlashCommandInteractionEvent event) {
		return event.getOption(ADD_BET_OPTION_ODDS).getAsDouble();
	}
}
