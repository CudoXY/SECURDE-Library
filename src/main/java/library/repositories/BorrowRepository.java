package library.repositories;

import library.domain.Borrow;
import library.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BorrowRepository extends CrudRepository<Borrow, Integer>{

	@Transactional
	Borrow findFirstByMaterial_IdAndBorrower_Id(String materialId, int borrowerId);
}
