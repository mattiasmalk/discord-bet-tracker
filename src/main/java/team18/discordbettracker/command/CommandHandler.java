package team18.discordbettracker.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@NullMarked
public class CommandHandler {
	private final Map<String, SlashCommand> commands = new HashMap<>();

	public CommandHandler(List<SlashCommand> commands) {
		commands.forEach(command -> this.commands.put(command.getName(), command));
	}

	public void handle(SlashCommandInteractionEvent event) {
		var command = commands.get(event.getName());

		if (command == null) {
			event.reply("Command not found").setEphemeral(true).queue();
			return;
		}

		command.execute(event);
	}
}
