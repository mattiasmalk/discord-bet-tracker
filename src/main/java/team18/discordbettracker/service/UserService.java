package team18.discordbettracker.service;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import team18.discordbettracker.model.User;
import team18.discordbettracker.model.UserId;
import team18.discordbettracker.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Service
@NullMarked
@AllArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public void register(Long discordId, Long serverId, String name) {
		System.out.println("Registering user: " + name);
		var userId = new UserId(discordId, serverId);
		var user = User.builder()
				.userId(userId)
				.name(name)
				.createdAt(Instant.now())
				.build();

		userRepository.save(user);
	}

	public Optional<User> findByUserId(UserId userId) {
		return userRepository.findById(userId);
	}

	public User createUser(UserId userId, String name) {
		return userRepository.save(User.builder()
				.userId(userId)
				.name(name)
				.createdAt(Instant.now())
				.build());
	}
}
