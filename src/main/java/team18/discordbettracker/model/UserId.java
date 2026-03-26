package team18.discordbettracker.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;

import java.io.Serializable;

@Embeddable
public record UserId(
		@Column(name = "user_id") Long userId,
		@Column(name = "server_id") Long serverId)
		implements Serializable {
}
