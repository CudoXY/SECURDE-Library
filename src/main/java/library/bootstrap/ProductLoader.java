package library.bootstrap;

import library.domain.Material;
import library.domain.SecretQuestion;
import library.repositories.MaterialRepository;
import library.repositories.SecretQuestionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader implements ApplicationListener<ContextRefreshedEvent>
{

	private MaterialRepository materialRepository;
	private SecretQuestionRepository secretQuestionRepository;

	private Logger log = Logger.getLogger(ProductLoader.class);

	@Autowired
	public void setMaterialRepository(MaterialRepository materialRepository)
	{
		this.materialRepository = materialRepository;
	}

	@Autowired
	public void setSecretQuestionRepository(SecretQuestionRepository secretQuestionRepository)
	{
		this.secretQuestionRepository = secretQuestionRepository;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
//		SecretQuestion s = new SecretQuestion();
//		s.setQuestion("What was the name of your elementary school?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("Where does your nearest sibling live?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("What is the name of your first pet?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("Where would like to live the most?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("What is your most favorite food?");
//		secretQuestionRepository.save(s);
//
//		s = new SecretQuestion();
//		s.setQuestion("test question poh");
//		secretQuestionRepository.save(s);


//        Product shirt = new Product();
//        shirt.setDescription("Spring Framework Guru Shirt");
//        shirt.setPrice(new BigDecimal("18.95"));
//        shirt.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_shirt-rf412049699c14ba5b68bb1c09182bfa2_8nax2_512.jpg");
//        shirt.setProductId("235268845711068308");
//        materialRepository.save(shirt);
//
//        log.info("Saved Shirt - id: " + shirt.getId());
//
//        Product mug = new Product();
//        mug.setDescription("Spring Framework Guru Mug");
//        mug.setImageUrl("https://springframework.guru/wp-content/uploads/2015/04/spring_framework_guru_coffee_mug-r11e7694903c348e1a667dfd2f1474d95_x7j54_8byvr_512.jpg");
//        mug.setProductId("168639393495335947");
//        mug.setPrice(new BigDecimal("11.95"));
//        materialRepository.save(mug);
//
//        log.info("Saved Mug - id:" + mug.getId());

        Material m = new Material();
        m.setId("book1");
        m.setAuthor("Author 1");
        m.setCategory(1);
        m.setPublisher("Publisher 1");
        m.setTitle("Book 1");
        m.setYear(2017);
        materialRepository.save(m);

        m = new Material();
        m.setId("book2");
        m.setAuthor("Author 2");
        m.setCategory(1);
        m.setPublisher("Publisher 2");
        m.setTitle("Book 2");
        m.setYear(2010);
        materialRepository.save(m);

        m = new Material();
        m.setId("book3");
        m.setAuthor("Author 1");
        m.setCategory(1);
        m.setPublisher("Publisher 1");
        m.setTitle("Book 3");
        m.setYear(2017);
        materialRepository.save(m);

        m = new Material();
        m.setId("book4");
        m.setAuthor("Author 1");
        m.setCategory(1);
        m.setPublisher("Publisher 2");
        m.setTitle("Book 4");
        m.setYear(2012);
        materialRepository.save(m);

        m = new Material();
        m.setId("book5");
        m.setAuthor("Author 1");
        m.setCategory(1);
        m.setPublisher("Publisher 3");
        m.setTitle("Book 5");
        m.setYear(2015);
        materialRepository.save(m);

        // Magazine

        m = new Material();
        m.setId("magazine1");
        m.setAuthor("Author 3");
        m.setCategory(2);
        m.setPublisher("Publisher 4");
        m.setTitle("Magazine 3");
        m.setYear(2017);
        materialRepository.save(m);

        m = new Material();
        m.setId("magazine2");
        m.setAuthor("Author 3");
        m.setCategory(2);
        m.setPublisher("Publisher 4");
        m.setTitle("Magazine 3");
        m.setYear(2015);
        materialRepository.save(m);

        m = new Material();
        m.setId("magazine3");
        m.setAuthor("Author 3");
        m.setCategory(2);
        m.setPublisher("Publisher 4");
        m.setTitle("Magazine 4");
        m.setYear(2017);
        materialRepository.save(m);

        m = new Material();
        m.setId("magazine3");
        m.setAuthor("Author 4");
        m.setCategory(2);
        m.setPublisher("Publisher 5");
        m.setTitle("Magazine 5");
        m.setYear(2016);
        materialRepository.save(m);

        // Thesis

        m = new Material();
        m.setId("thesis1");
        m.setAuthor("Author 6");
        m.setCategory(3);
        m.setPublisher("Publisher 6");
        m.setTitle("Thesis 1");
        m.setYear(2017);
        materialRepository.save(m);

        m = new Material();
        m.setId("thesis2");
        m.setAuthor("Author 7");
        m.setCategory(3);
        m.setPublisher("Publisher 7");
        m.setTitle("Thesis 2");
        m.setYear(2017);
        materialRepository.save(m);
	}
}
