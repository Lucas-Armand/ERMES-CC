package com.ermes.api.models;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "businesses")
public class Business
{	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull(message = "Field 'name' can't be null.")
	private String name;
	
	@NotNull(message = "Field 'openingTime' can't be null.")
	LocalTime openingTime;
	
	@NotNull(message = "Field 'closingTime' can't be null.")
	LocalTime closingTime;
    
	@NotNull(message = "Field 'fieldOfActivity' can't be null.")
    String fieldOfActivity;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "business_id")
	@JsonIgnoreProperties({"name", "description", "duration", "price"})
	List<Service> services;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "business_id")
	@JsonIgnoreProperties({"prefix", "name", "surname", "schedules"})
	List<Employee> employees;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "business_id")
	@JsonIgnoreProperties({"name", "surname", "phone", "email", "address"})
	List<Customer> customers;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "business_id")
	@JsonIgnoreProperties({"date", "time", "service", "customer", "employee", "status"})
	List<Appointment> appointments;

	public Business()
	{
		super();
	}

	public Business(String name, LocalTime openingTime, LocalTime closingTime, String fieldOfActivity)
	{
		super();
		this.name = name;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
		this.fieldOfActivity = fieldOfActivity;
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

	public LocalTime getOpeningTime()
	{
		return openingTime;
	}

	public void setOpeningTime(LocalTime openingTime)
	{
		this.openingTime = openingTime;
	}

	public LocalTime getClosingTime()
	{
		return closingTime;
	}

	public void setClosingTime(LocalTime closingTime)
	{
		this.closingTime = closingTime;
	}

	public String getFieldOfActivity()
	{
		return fieldOfActivity;
	}

	public void setFieldOfActivity(String fieldOfActivity)
	{
		this.fieldOfActivity = fieldOfActivity;
	}

	public List<Service> getServices()
	{
		return services;
	}

	public void setServices(List<Service> services)
	{
		this.services = services;
	}

	public List<Employee> getEmployees()
	{
		return employees;
	}

	public void setEmployees(List<Employee> employees)
	{
		this.employees = employees;
	}

	public List<Customer> getCustomers()
	{
		return customers;
	}

	public void setCustomers(List<Customer> customers)
	{
		this.customers = customers;
	}

	public List<Appointment> getAppointments()
	{
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments)
	{
		this.appointments = appointments;
	}
}