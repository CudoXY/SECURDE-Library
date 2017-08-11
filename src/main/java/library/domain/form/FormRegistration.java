package library.domain.form;

import library.domain.Role;
import library.domain.SecretQuestion;

import javax.validation.constraints.NotNull;

/**
 * Created by CudoXY on 8/10/2017.
 */
public class FormRegistration
{
	@NotNull
	private String firstName;

	private String middleName;

	@NotNull
	private String lastName;

	@NotNull
	private int id;

	@NotNull
	private String password;

	@NotNull
	private String passwordRepeat;

	@NotNull
	private String email;

	@NotNull
	private Role role;

	@NotNull
	private int month;

	@NotNull
	private int day;

	@NotNull
	private int year;

	@NotNull
	private SecretQuestion secretQuestion;

	@NotNull
	private String secretAnswer;

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getMiddleName()
	{
		return middleName;
	}

	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPasswordRepeat()
	{
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat)
	{
		this.passwordRepeat = passwordRepeat;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Role getRole()
	{
		return role;
	}

	public void setRole(Role role)
	{
		this.role = role;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public SecretQuestion getSecretQuestion()
	{
		return secretQuestion;
	}

	public void setSecretQuestion(SecretQuestion secretQuestion)
	{
		this.secretQuestion = secretQuestion;
	}

	public String getSecretAnswer()
	{
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer)
	{
		this.secretAnswer = secretAnswer;
	}
}
