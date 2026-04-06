package team18.discordbettracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
@Builder
@Table(name = "bet")
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
	@Id
	@GeneratedValue
	private Long id;

	private String description;

	@Column(name = "stake", scale = 3)
	private BigDecimal stake;

	@Column(name = "odds", scale = 3)
	private BigDecimal odds;

	@Enumerated(EnumType.STRING)
	private BetStatus status;

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

}
