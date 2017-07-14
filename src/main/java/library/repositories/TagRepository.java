package library.repositories;

import library.domain.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer>{

	List<Tag> findAllByMaterial(int materialId);
}
