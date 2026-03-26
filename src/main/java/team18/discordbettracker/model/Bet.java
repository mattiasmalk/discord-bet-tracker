package team18.discordbettracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Table(name = "bet")
@NoArgsConstructor
public class Bet {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "user_id", referencedColumnName = "user_id"),
			@JoinColumn(name = "server_id", referencedColumnName = "server_id")
	})
	private User user;
	@Column(name = "created_at")
	private Instant createdAt;
	@Column(name = "resolved_at")
	private Instant resolvedAt;

	// TODO @Mattias - add required fields for bets
}
