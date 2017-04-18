package com.ermes.api.models;

import java.time.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "schedules")
public class Schedule
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull(message = "Field 'weekday' can't be null.")
	DayOfWeek weekday;

	@NotNull(message = "Field 'startWork' can't be null.")
	LocalTime startWork;

	@NotNull(message = "Field 'endWork' can't be null.")
	LocalTime endWork;

	@NotNull(message = "Field 'startBreak' can't be null.")
	LocalTime startBreak;

	@NotNull(message = "Field 'endBreak' can't be null.")
	LocalTime endBreak;

	@NotNull(message = "Field 'isAvailable' can't be null.")
	boolean isAvailable;

	public Schedule()
	{
		super();
	}

	public Schedule(DayOfWeek weekday, LocalTime startWork, LocalTime endWork, LocalTime startBreak, LocalTime endBreak,
			boolean isAvailable)
	{
		super();
		this.weekday = weekday;
		this.startWork = startWork;
		this.endWork = endWork;
		this.startBreak = startBreak;
		this.endBreak = endBreak;
		this.isAvailable = isAvailable;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DayOfWeek getWeekday()
	{
		return weekday;
	}

	public void setWeekday(DayOfWeek weekday)
	{
		this.weekday = weekday;
	}

	public LocalTime getStartWork()
	{
		return startWork;
	}

	public void setStartWork(LocalTime startWork)
	{
		this.startWork = startWork;
	}

	public LocalTime getEndWork()
	{
		return endWork;
	}

	public void setEndWork(LocalTime endWork)
	{
		this.endWork = endWork;
	}

	public LocalTime getStartBreak()
	{
		return startBreak;
	}

	public void setStartBreak(LocalTime startBreak)
	{
		this.startBreak = startBreak;
	}

	public LocalTime getEndBreak()
	{
		return endBreak;
	}

	public void setEndBreak(LocalTime endBreak)
	{
		this.endBreak = endBreak;
	}

	public boolean isAvailable()
	{
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable)
	{
		this.isAvailable = isAvailable;
	}

}
