package library.repositories;

import library.domain.Material;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MaterialRepository extends CrudRepository<Material, Integer>{

	@Transactional
	List<Material> findAllByCategory(int category);

	@Transactional
	Material findById(String id);

	@Transactional
	void deleteById(String id);


}
