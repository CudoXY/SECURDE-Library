package library.repositories;

import library.domain.Borrow;
import library.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

public interface BorrowRepository extends CrudRepository<Borrow, Integer>{

	// Active Borrow
	@Transactional
	Borrow findFirstByMaterial_IdAndDateBorrowedIsNotNullAndDateReturnedIsNull(String materialId);

	// Reserve
	@Transactional
	Borrow findFirstByMaterial_IdAndBorrower_IdAndDateReturnedIsNull(String materialId, int borrowerId);
	Borrow findFirstByMaterial_IdAndBorrower_IdAndDateBorrowedIsNullAndDateReturnedIsNull(String materialId, int borrowerId);

	@Transactional
	List<Borrow> findAllByMaterial_IdAndDateBorrowedIsNull(String materialId);

	@Transactional
	Borrow findFirstByMaterial_IdAndBorrower_IdAndDateBorrowedIsNotNullAndIsReleasedIsTrue(String materialId, int borrowerId);

	@Transactional
	Borrow findFirstByMaterial_IdAndBorrower_Id(String materialId, int borrowerId);

	@Transactional
	Borrow findFirstByMaterial_IdOrderByDateBorrowedDesc(String materialId);

	@Transactional
	Borrow findById(int id);



}
