package com.ermes.api.models;

import java.time.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "appointments")
public class Appointment
{	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Field 'date' can't be null.")
	@Column(columnDefinition = "DATE")
	LocalDate date;
	
	@NotNull(message = "Field 'time' can't be null.")
	@Column(columnDefinition = "TIME")
	LocalTime time;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "service_id")
	@NotNull(message = "Field 'service' can't be null.")
	Service service;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
	@NotNull(message = "Field 'customer' can't be null.")
	Customer customer;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "employee_id")
	@NotNull(message = "Field 'employee' can't be null.")
	Employee employee;
	
	@NotNull(message = "Field 'status' can't be null.")
	String status;

	public Appointment()
	{
		super();
	}

	public Appointment(LocalDate date, LocalTime time, Service service, Customer customer, Employee employee, String status)
	{
		super();
		this.date = date;
		this.time = time;
		this.service = service;
		this.customer = customer;
		this.employee = employee;
		this.status = status;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public LocalDate getDate()
	{
		return date;
	}

	public void setDate(LocalDate date)
	{
		this.date = date;
	}

	public LocalTime getTime()
	{
		return time;
	}

	public void setTime(LocalTime time)
	{
		this.time = time;
	}

	public Service getService()
	{
		return service;
	}

	public void setService(Service service)
	{
		this.service = service;
	}

	public Customer getCustomer()
	{
		return customer;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public void setEmployee(Employee employee)
	{
		this.employee = employee;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}
}
