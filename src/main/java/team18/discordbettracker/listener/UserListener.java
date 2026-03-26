package team18.discordbettracker.listener;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;
import team18.discordbettracker.command.CommandHandler;

@AllArgsConstructor
@NullMarked
@Component
public class UserListener extends ListenerAdapter {
	private final CommandHandler commandHandler;

	@Override
	public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
		commandHandler.handle(event);
	}
}
