package library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.ALL})
    private User user;

    @NotNull
    @ManyToOne(cascade = {CascadeType.ALL})
    private Material material;

    @NotNull
    private Timestamp dateReviewed;

    @NotNull
    private String message;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    public Timestamp getDateReviewed()
    {
        return dateReviewed;
    }

    public void setDateReviewed(Timestamp dateReviewed)
    {
        this.dateReviewed = dateReviewed;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
