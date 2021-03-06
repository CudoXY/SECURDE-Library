package library.repositories;

import library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>
{
	@Transactional
	User findOneById(int id);

	@Transactional
	User findOneByEmail(String email);
	User findByResetToken(String resetToken);
	@Transactional
    void deleteById(int id);

	@Transactional
	User findById(int id);
}
