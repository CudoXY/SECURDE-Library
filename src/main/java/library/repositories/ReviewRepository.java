package library.repositories;

import library.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends CrudRepository<Review, Integer>{

	@Transactional
	Iterable<Review> findAllByMaterial_Id(String materialId);

	@Transactional
	Iterable<Review> findAllByMaterial_IdOrderByDateReviewedDesc(String materialId);
}
