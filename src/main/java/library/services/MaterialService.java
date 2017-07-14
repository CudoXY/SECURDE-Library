package library.services;


import library.domain.Material;

public interface MaterialService
{
	Iterable<Material> getMaterialList();

	Iterable<Material> getMaterialListByCategory(int category);

	Iterable<Material> getMaterialByKeyword(String keyword);

	Material getMaterialById(String id);

	//    Staff
	Material saveMaterial(Material material);
}
