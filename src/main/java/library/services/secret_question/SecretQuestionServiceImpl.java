package library.services.secret_question;

import library.domain.SecretQuestion;
import library.repositories.SecretQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecretQuestionServiceImpl implements SecretQuestionService
{
    private SecretQuestionRepository secretQuestionRepository;

    @Autowired
    public void setSecretQuestionRepository(SecretQuestionRepository secretQuestionRepository)
    {
        this.secretQuestionRepository = secretQuestionRepository;
    }

    @Override
    public Iterable<SecretQuestion> getAll()
    {
        return secretQuestionRepository.findAll();
    }
}
