package library.repositories;

import library.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>
{
	User findOneById(int id);
	Optional<User> findOneByUsername(String username);
	Optional<User> findOneByEmail(String email);
}
