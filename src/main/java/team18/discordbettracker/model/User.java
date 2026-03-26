package team18.discordbettracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user")
@Builder
@AllArgsConstructor
public class User {
	@EmbeddedId
	private UserId userId;
	private String name;
	@Column(name = "created_at")
	private Instant createdAt;
	@Column(name = "deleted_at")
	private Instant deletedAt;
}
