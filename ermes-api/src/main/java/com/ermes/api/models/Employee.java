package com.ermes.api.models;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
public class Employee
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Field 'name' can't be null.")
	private String name;
	
	@NotNull(message = "Field 'surname' can't be null.")
	private String surname;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "employee_id")
	List<Schedule> schedules;

	public Employee()
	{
		super();
	}

	public Employee(String name, String surname, List<Schedule> schedules)
	{
		super();
		this.name = name;
		this.surname = surname;
		this.schedules = schedules;
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

	public List<Schedule> getSchedules()
	{
		return schedules;
	}

	public void setSchedules(List<Schedule> schedule)
	{
		this.schedules = schedule;
	}
}
