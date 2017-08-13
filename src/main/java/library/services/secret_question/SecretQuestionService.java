package library.services.secret_question;


import library.domain.SecretQuestion;

public interface SecretQuestionService
{
	Iterable<SecretQuestion> getAll();
}
