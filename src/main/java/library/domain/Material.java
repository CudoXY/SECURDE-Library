package library.domain;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Indexed
public class Material
{
    @Id
    private String id;

    @Transient
    private String oldId;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String author;

    @NotNull
    @NotEmpty
    private String publisher;

    @NotNull
    private int year;

    @NotNull
    private int category;

    @Transient
    private Borrow borrowStatus;

    public Borrow getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Borrow borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getCategory()
    {
        return category;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }


    public String getOldId()
    {
        return oldId;
    }

    public void setOldId(String oldId)
    {
        this.oldId = oldId;
    }
}
