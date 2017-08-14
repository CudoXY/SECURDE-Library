package library.services.tag;


import library.domain.Tag;

public interface TagService
{
	Iterable<Tag> getTagList();

	Iterable<Tag> getTagListByMaterialId(int materialId);

	Tag addTag(Tag tag);
}
