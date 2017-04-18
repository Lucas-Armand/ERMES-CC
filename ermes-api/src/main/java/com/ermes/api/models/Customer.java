package com.ermes.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Field 'name' can't be null.")
	private String name;
	
	@NotNull(message = "Field 'surname' can't be null.")
	private String surname;
	
	@NotNull(message = "Field 'phone' can't be null.")
	private String phone;

	@NotNull(message = "Field 'email' can't be null.")
	private String email;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
	private Address address;

	public Customer()
	{
		super();
	}	

	public Customer(String name, String surname, String phone, String email, Address address)
	{
		super();
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
		this.address = address;
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

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Address getAddress()
	{
		return address;
	}

	public void setAddress(Address address)
	{
		this.address = address;
	}
}