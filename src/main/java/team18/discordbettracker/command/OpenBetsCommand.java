package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.formatter.BetHistoryFormatter;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.service.BetService;

import static team18.discordbettracker.util.BotHelpers.getIdFromGuild;

@NullMarked
@Component
@AllArgsConstructor
public class OpenBetsCommand implements SlashCommand {
    private final BetService betService;
    private final BetHistoryFormatter betHistoryFormatter;

    @Override
    public String getName() {
        return "open-bets";
    }

    @Override
    public String getDescription() {
        return "Show your open bets in this server.";
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        var discordUser = event.getUser();
        var guildId = getIdFromGuild(event.getGuild());
        var userId = new UserId(discordUser.getIdLong(), guildId);
        var bets = betService.getOpenBets(userId);
        var message = betHistoryFormatter.format(bets);

        event.reply(message).queue();
    }
}
