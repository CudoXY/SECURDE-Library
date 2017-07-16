package library.repositories;

import library.domain.Borrow;
import org.springframework.data.repository.CrudRepository;

public interface BorrowRepository extends CrudRepository<Borrow, Integer>{
}