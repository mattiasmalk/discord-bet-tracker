package team18.discordbettracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team18.discordbettracker.model.User;
import team18.discordbettracker.model.UserId;

public interface UserRepository extends JpaRepository<User, UserId> {
}
