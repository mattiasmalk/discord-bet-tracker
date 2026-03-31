package team18.discordbettracker.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication( scanBasePackages = "team18.discordbettracker")
public class DiscordBetTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscordBetTrackerApplication.class, args);
	}

}
