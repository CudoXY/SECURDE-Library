package library.services;

import library.domain.Material;
import library.domain.helper.Filter;
import library.repositories.MaterialRepository;
import library.repositories.TagRepository;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    // Spring will inject here the entity manager object
    @PersistenceContext
    private EntityManager entityManager;

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
    public Iterable<Material> search(Filter filter)
    {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager =
                org.hibernate.search.jpa.Search.
                        getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query DSL
        QueryBuilder queryBuilder =
                fullTextEntityManager.getSearchFactory()
                        .buildQueryBuilder().forEntity(Material.class).get();

        String field = "";

        switch (filter.getSearchFilter())
		{
			case TITLE:
				field = "title";
				break;
			case AUTHOR:
				field = "author";
				break;
			case PUBLISHER:
				field = "publisher";
				break;
		}

        // a very basic query by keywords
        org.apache.lucene.search.Query q =
                queryBuilder
                        .keyword()
                        .onField(field)
                        .matching(filter.getQuery())
                        .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery =
                fullTextEntityManager.createFullTextQuery(q, Material.class);

        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
        List results = jpaQuery.getResultList();

		System.out.println("query = " + filter.getQuery());
		System.out.println("field = " + field);
		System.out.println(results.toString());

        return results;
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
