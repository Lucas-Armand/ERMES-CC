package com.ermes.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "addresses")
public class Address
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@NotNull(message = "Field 'street' can't be null.")
	String street;
	
	@NotNull(message = "Field 'number' can't be null.")
	int number;
	
	@NotNull(message = "Field 'locality' can't be null.")
	String locality;
	
	@NotNull(message = "Field 'city' can't be null.")
	String city;

	public Address()
	{
	}

	public Address(String street, int number, String locality, String city)
	{
		super();
		this.street = street;
		this.number = number;
		this.locality = locality;
		this.city = city;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getStreet()
	{
		return street;
	}

	public void setStreet(String street)
	{
		this.street = street;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public String getLocality()
	{
		return locality;
	}

	public void setLocality(String locality)
	{
		this.locality = locality;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}	
}
