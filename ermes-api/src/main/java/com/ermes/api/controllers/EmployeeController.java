package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Employee;
import com.ermes.api.services.EmployeeService;

@RestController
@RequestMapping("/businesses/{businessId}/employees")
public class EmployeeController
{
	@Autowired
	private EmployeeService employeeService;

	// GET /businesses/{businessId}/employees
	// Retrieves the employees of a business

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getEmployees(@PathVariable Long businessId)
	{
		return employeeService.getEmployees(businessId);
	}

	// POST /businesses/{businessId}/employees
	// Creates a new employee for a business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createEmployee(@PathVariable Long businessId, @Validated @RequestBody Employee employee)
	{
		return employeeService.saveEmployee(businessId, employee);
	}

	// GET /businesses/{businessId}/employees/{employeeId}
	// Retrieves a specific employee of business

	@RequestMapping(value = "/{employeeId}")
	ResponseEntity<?> getEmployeeById(@PathVariable Long businessId, @PathVariable Long employeeId)
	{
		return employeeService.getEmployeeById(businessId, employeeId);
	}

	// PUT /businesses/{businessId}/employees/{employeeId}
	// Updates a specific employee of business

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateEmployeeById(@PathVariable Long businessId, @PathVariable Long employeeId,
			@Validated @RequestBody Employee employee)
	{
		return employeeService.updateEmployeeById(businessId, employeeId, employee);
	}

	// DELETE /businesses/{businessId}/employees/{employeeId}
	// Deletes a specific employee of a business

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteEmployeeById(@PathVariable Long businessId, @PathVariable Long employeeId)
	{
		return employeeService.deleteEmployeeById(businessId, employeeId);
	}
}
