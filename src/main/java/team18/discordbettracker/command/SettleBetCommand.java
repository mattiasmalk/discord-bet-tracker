package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.model.BetStatus;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.service.BetService;

import java.util.List;
import java.util.Locale;

import static team18.discordbettracker.util.BotHelpers.getIdFromGuild;

@NullMarked
@Component
@AllArgsConstructor
public class SettleBetCommand implements SlashCommand {
    public static final String SETTLE_BET_OPTION_BET_ID = "bet-id";
    public static final String SETTLE_BET_OPTION_RESULT = "result";

    private final BetService betService;

    @Override
    public String getName() {
        return "settle";
    }

    @Override
    public String getDescription() {
        return "Settle an open bet by ID.";
    }

    @Override
    public List<OptionData> getRequiredOptions() {
        return List.of(
                new OptionData(OptionType.INTEGER, SETTLE_BET_OPTION_BET_ID, "ID of the bet to settle", true),
                new OptionData(OptionType.STRING, SETTLE_BET_OPTION_RESULT, "Result of the bet (WON, LOST, PUSH, VOID)", true)
        );
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var betIdOption = event.getOption(SETTLE_BET_OPTION_BET_ID);
        var resultOption = event.getOption(SETTLE_BET_OPTION_RESULT);

        if (betIdOption == null || resultOption == null) {
            event.reply("Missing bet ID or result option.").setEphemeral(true).queue();
            return;
        }

        long betId = betIdOption.getAsLong();
        var resultText = resultOption.getAsString().trim().toUpperCase(Locale.ROOT);
        BetStatus result;

        try {
            result = BetStatus.valueOf(resultText);
        } catch (IllegalArgumentException ex) {
            event.reply("Invalid result. Use WON, LOST, PUSH or VOID.").setEphemeral(true).queue();
            return;
        }

        var discordUser = event.getUser();
        var guildId = getIdFromGuild(event.getGuild());
        var userId = new UserId(discordUser.getIdLong(), guildId);

        try {
            var bet = betService.settleBet(userId, betId, result);
            event.reply("Bet %d settled as %s. Profit: %s"
                            .formatted(bet.id(), bet.status(), bet.profit()))
                    .queue();
        } catch (IllegalArgumentException | IllegalStateException ex) {
            event.reply(ex.getMessage()).setEphemeral(true).queue();
        }
    }
}
