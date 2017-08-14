package library.repositories;

import library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>
{
	User findOneByEmail(String email);
	@Transactional
    void deleteById(int id);
}
