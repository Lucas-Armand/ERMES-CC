package com.ermes.api.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
public class Authority
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull(message = "Field 'email' can't be null.")
	String email;
	
	@NotNull(message = "Field 'authority' can't be null.")
	private String authority;

	public Authority()
	{
		super();
	}
	
	public Authority(String email, String authority)
	{
		super();
		this.email = email;
		this.authority = authority;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
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

	public String getAuthority()
	{
		return authority;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}
}
