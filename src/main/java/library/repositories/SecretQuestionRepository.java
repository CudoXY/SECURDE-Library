package library.repositories;

import library.domain.Borrow;
import library.domain.SecretQuestion;
import org.springframework.data.repository.CrudRepository;

public interface SecretQuestionRepository extends CrudRepository<SecretQuestion, Integer>{
}
