package com.ermes.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Field 'email' can't be null.")
	private String email;

	@NotNull(message = "Field 'password' can't be null.")
	//@JsonIgnore
	private String password;
	
	@NotNull(message = "Field 'authority' can't be null.")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "authority_id")
	@JsonIgnore
	private Authority authority;
	
	@NotNull(message = "Field 'business' can't be null.")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "business_id")
	private Business business;

	public User()
	{
		super();
	}

	public User(String email, String password, Authority authority, Business business)
	{
		super();
		this.email = email;
		this.password = password;
		this.authority = authority;	
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public Authority getAuthority()
	{
		return authority;
	}

	public void setAuthority(Authority authority)
	{
		this.authority = authority;
	}

	public Business getBusiness()
	{
		return business;
	}

	public void setBusiness(Business business)
	{
		this.business = business;
	}
}
