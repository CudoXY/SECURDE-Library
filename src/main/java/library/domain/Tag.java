package library.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Tag
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@NotNull
	@ManyToOne(cascade = {CascadeType.ALL})
	private Material material;

	@NotNull
	private String name;


	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
