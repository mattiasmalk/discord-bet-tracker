package team18.discordbettracker.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public interface SlashCommand {
	String getName();
	String getDescription();

	default List<OptionData> getRequiredOptions() {
		return List.of();
	}
	default List<OptionData> getNonRequiredOptions() {
		return List.of();
	}

	default CommandData getCommandData() {
		var requiredOptions = getRequiredOptions();
		var nonRequiredOptions = getNonRequiredOptions();
		var commandData = Commands.slash(getName(), getDescription());
		if (requiredOptions != null) {
			commandData.addOptions(requiredOptions);
		}
		if (nonRequiredOptions != null) {
			commandData.addOptions(nonRequiredOptions);
		}
		return commandData;
	}
	void execute(SlashCommandInteractionEvent event);
}
