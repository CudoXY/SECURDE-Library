package library.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Borrow
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private User borrower;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Material material;

    private Timestamp dateBorrowed;
    private Timestamp dateReturned;
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getBorrower()
    {
        return borrower;
    }

    public void setBorrower(User borrower)
    {
        this.borrower = borrower;
    }

    public Material getMaterial()
    {
        return material;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    public Timestamp getDateBorrowed()
    {
        return dateBorrowed;
    }

    public void setDateBorrowed(Timestamp dateBorrowed)
    {
        this.dateBorrowed = dateBorrowed;
    }

    public Timestamp getDateReturned()
    {
        return dateReturned;
    }

    public void setDateReturned(Timestamp dateReturned)
    {
        this.dateReturned = dateReturned;
    }
}