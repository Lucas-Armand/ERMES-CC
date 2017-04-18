package com.ermes.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "services")
public class Service
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@NotNull(message = "Field 'name' can't be null.")
	String name;

	@NotNull(message = "Field 'description' can't be null.")
	String description;

	@NotNull(message = "Field 'duration' can't be null.")
	int duration;

	@NotNull(message = "Field 'price' can't be null.")
	double price = 0.0;

	public Service()
	{
		super();
	}

	public Service(String name, String description, int duration, double price)
	{
		super();
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.price = price;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}
}
