package library.services;

import library.domain.Material;
import library.repositories.MaterialRepository;
import library.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {

    private TagRepository tagRepository;
    private MaterialRepository materialRepository;

    @Autowired
    public void setMaterialRepository(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Iterable<Material> getMaterialList()
    {
        return materialRepository.findAll();
    }

    @Override
    public Iterable<Material> getMaterialListByCategory(int category)
    {
        return materialRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Material> getMaterialByKeyword(String keyword)
    {
        // TODO:
        return materialRepository.findAll();
    }

    @Override
    public Material getMaterialById(String id)
    {
        System.out.println("\tgetMaterialById");
        return materialRepository.findById(id);
    }

    @Override
    public Material saveMaterial(Material material)
    {
        return materialRepository.save(material);
    }
}
