package library.services;

import library.domain.Tag;
import library.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Iterable<Tag> getTagList()
    {
        return tagRepository.findAll();
    }

    @Override
    public Iterable<Tag> getTagListByMaterialId(int materialId)
    {
        return tagRepository.findAllByMaterial(materialId);
    }

    @Override
    public Tag addTag(Tag tag)
    {
        return tagRepository.save(tag);
    }
}
