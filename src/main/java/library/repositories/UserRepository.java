package library.repositories;

import library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>
{
	@Transactional
	User findOneByEmail(String email);
	@Transactional
	User findUsersById(int id);
	@Transactional
	void deleteById(int id);
}
