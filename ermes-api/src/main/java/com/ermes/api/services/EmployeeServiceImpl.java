package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Business;
import com.ermes.api.models.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService
{	
	@Autowired
	BusinessService businessService;

	@Override
	public ResponseEntity<List<Employee>> getEmployees(Long businessId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		List<Employee> employees = business.getEmployees();
		
		if (!employees.isEmpty())
		{
			return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Employee>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employee> getEmployeeById(Long businessId, Long employeeId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();

		for (Employee employee : business.getEmployees())
		{
			if (employee.getId() == employeeId)
			{
				return new ResponseEntity<Employee>(employee, HttpStatus.OK);
			}
		}

		return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Employee> saveEmployee(Long businessId, Employee employee)
	{
		try
		{
			Business business = businessService.getBusinessById(businessId).getBody();
			business.getEmployees().add(employee);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Employee>(HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Employee>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Employee> updateEmployeeById(Long businessId, Long employeeId, Employee s)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Employee employee = getEmployeeById(businessId, employeeId).getBody();

		if (employee != null)
		{
			int index = business.getEmployees().indexOf(employee);
			s.setId(employeeId);
			business.getEmployees().set(index, s);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Employee>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employee> deleteEmployeeById(Long businessId, Long employeeId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Employee employee = getEmployeeById(businessId, employeeId).getBody();

		if (employee != null)
		{
			business.getEmployees().remove(employee);
			businessService.updateBusinessById(businessId, business);
			return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}
}
