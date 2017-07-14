package library.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class User
{
    @Id
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;
    private int role;
    private Date birthDate;
    private boolean isTemporary;
    private boolean isLocked;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn
    private SecretQuestion secretQuestion;

    private String secretAnswer;

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

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
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

    public int getRole()
    {
        return role;
    }

    public void setRole(int role)
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
}
