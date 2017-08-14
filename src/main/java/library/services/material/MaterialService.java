package library.services.material;


import library.domain.Material;
import library.domain.helper.Filter;

import java.util.List;

public interface MaterialService
{
	Iterable<Material> getMaterialList();

	Iterable<Material> search(Filter filter);

	Iterable<Material> getMaterialListByCategory(int category);

	Iterable<Material> getMaterialByKeyword(String keyword);

	Material getMaterialById(String id);

	//    Staff
	Material saveMaterial(Material material);

//	Material updateMaterial(Material material);

	void deleteMaterial(String materialID);

	Iterable<Material> getMaterialWithBorrowStatus();
}
