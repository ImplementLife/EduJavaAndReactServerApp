package il.test.TestWithReact.data.repo;

import il.test.TestWithReact.data.entity.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
