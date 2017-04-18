package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Employee;
import com.ermes.api.models.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService
{
	@Autowired
	EmployeeService employeeService;

	@Override
	public ResponseEntity<List<Schedule>> getSchedules(Long businessId, Long employeeId)
	{
		Employee employee = employeeService.getEmployeeById(businessId, employeeId).getBody();
		List<Schedule> schedules = employee.getSchedules();
		
		if (!schedules.isEmpty())
		{
			return new ResponseEntity<List<Schedule>>(schedules, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Schedule>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Schedule> getScheduleById(Long businessId, Long employeeId, Long scheduleId)
	{
		Employee employee = employeeService.getEmployeeById(businessId, employeeId).getBody();

		for (Schedule schedule : employee.getSchedules())
		{
			if (schedule.getId() == scheduleId)
			{
				return new ResponseEntity<Schedule>(schedule, HttpStatus.OK);
			}
		}

		return new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Schedule> saveSchedule(Long businessId, Long employeeId, Schedule schedule)
	{
		try
		{
			Employee employee = employeeService.getEmployeeById(businessId, employeeId).getBody();
			employee.getSchedules().add(schedule);
			employeeService.updateEmployeeById(businessId, employeeId, employee);

			return new ResponseEntity<Schedule>(HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Schedule>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Schedule> updateScheduleById(Long businessId, Long employeeId, Long scheduleId, Schedule s)
	{
		Employee employee = employeeService.getEmployeeById(businessId, employeeId).getBody();
		Schedule schedule = getScheduleById(businessId, employeeId, scheduleId).getBody();

		if (schedule != null)
		{
			int index = employee.getSchedules().indexOf(schedule);
			s.setId(scheduleId);
			employee.getSchedules().set(index, s);
			employeeService.updateEmployeeById(businessId, employeeId, employee);

			return new ResponseEntity<Schedule>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Schedule> deleteScheduleById(Long businessId, Long employeeId, Long scheduleId)
	{
		Employee employee = employeeService.getEmployeeById(businessId, employeeId).getBody();
		Schedule schedule = getScheduleById(businessId, employeeId, scheduleId).getBody();

		if (schedule != null)
		{
			employee.getSchedules().remove(schedule);
			employeeService.updateEmployeeById(businessId, employeeId, employee);
			return new ResponseEntity<Schedule>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<Schedule>(HttpStatus.NOT_FOUND);
		}
	}
}
