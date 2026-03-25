package team18.discordbettracker.command;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NullMarked
@AllArgsConstructor
public class SlashCommandRegistrar {
	private final JDA jda;
	private final List<SlashCommand> commands;

	@PostConstruct
	public void registerCommands() {

		var commandData = commands.stream().map(SlashCommand::getCommandData).toList();
//		jda.updateCommands().addCommands(commandData).queue();

		var guildId = "1485352818184552459";

		jda.getGuildById(guildId).updateCommands().addCommands(commandData).queue();

		System.out.println("Slash commands registered: " + commandData.size());
	}
}
