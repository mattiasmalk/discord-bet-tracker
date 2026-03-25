package team18.discordbettracker.config;

import lombok.AllArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team18.discordbettracker.listener.UserListener;

@Configuration
@AllArgsConstructor
public class BotConfig {

	@Bean
	public JDA jda(@Value("${discord.bot.token}") String token, UserListener userListener) throws InterruptedException {
		var jda = JDABuilder.createDefault(token,
						GatewayIntent.GUILD_MESSAGES,
						GatewayIntent.MESSAGE_CONTENT)
				.addEventListeners(userListener)
				.build();
		jda.awaitReady();

		return jda;
	}
}
