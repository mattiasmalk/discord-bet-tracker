package team18.discordbettracker.repository;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

@Repository
@NullMarked
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {
	private final EntityManager entityManager;

}
