package library.domain;

import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Borrow
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull
    @ManyToOne
    private User borrower;

    @NotNull
    @ManyToOne
    private Material material;

    private java.sql.Date dateBorrowed;

    private java.sql.Date dateReturned;

    private boolean isReleased;

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

    public java.sql.Date getDateBorrowed()
    {
        return dateBorrowed;
    }

    public void setDateBorrowed(java.sql.Date dateBorrowed)
    {
        this.dateBorrowed = dateBorrowed;
    }

    public java.sql.Date getDateReturned()
    {
        return dateReturned;
    }

    public void setDateReturned(java.sql.Date dateReturned)
    {
        this.dateReturned = dateReturned;
    }

    @Transient
    public Date getExpectedReturnDate()
    {
        if (dateBorrowed == null)
            return null;

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateBorrowed);
        cal.add(Calendar.DAY_OF_WEEK, borrower.getRole() == Role.ROLE_STUDENT ? 7 : 14);

        return new Date(cal.getTimeInMillis());
    }

    public boolean isReleased()
    {
        return isReleased;
    }

    public void setReleased(boolean released)
    {
        isReleased = released;
    }
}
