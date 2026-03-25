package team18.discordbettracker.command;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.service.UserService;

@AllArgsConstructor
@NullMarked
@Component
public class RegisterCommand implements SlashCommand {
	private final UserService userService;

	@Override
	public String getName() {
		return "register";
	}

	@Override
	public String getDescription() {
		return "Register the user to the database";
	}

	@Override
	public CommandData getCommandData() {
		return Commands.slash(getName(), getDescription());
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		var user = event.getUser();
		var guildId = event.getGuild() != null ? event.getGuild().getIdLong() : 0L;
		userService.register(user.getIdLong(), guildId, user.getName());
		event.reply("Registered %s (%s, %s)".formatted(user.getName(), user.getIdLong(), guildId)).queue();
	}
}
