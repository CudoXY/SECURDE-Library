package library.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class MaterialReserve
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(cascade = {CascadeType.ALL})
    private User borrower;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Material material;

    private Timestamp dateReserved;

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

    public Timestamp getDateReserved()
    {
        return dateReserved;
    }

    public void setDateReserved(Timestamp dateReserved)
    {
        this.dateReserved = dateReserved;
    }
}
