package library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
public class User
{
    @Id
	private int id;

	private String firstName;

	private String middleName;

	private String lastName;

	@NotNull
	private String password;

	@Column(unique = true)
	@Size(min = 6, message = "Email cannot be blank")
	private String email;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private Role role;

	private Date birthDate;

	private boolean isTemporary;

	private boolean isLocked;

	@ManyToOne
	private SecretQuestion secretQuestion;

	private String secretAnswer;

	private Date dateRegistered;

    private String resetToken;

	public User()
	{
		birthDate = new Date(new java.util.Date().getTime());
	}

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

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

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	public boolean isTemporary()
	{
		return isTemporary;
	}

	public void setTemporary(boolean temporary)
	{
		isTemporary = temporary;
	}

	public boolean isLocked()
	{
		return isLocked;
	}

	public void setLocked(boolean locked)
	{
		isLocked = locked;
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

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
