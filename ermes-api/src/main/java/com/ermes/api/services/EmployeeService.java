package com.ermes.api.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ermes.api.models.Employee;

public interface EmployeeService
{
	ResponseEntity<List<Employee>> getEmployees(Long businessId);

	ResponseEntity<Employee> getEmployeeById(Long businessId, Long employeeId);

	ResponseEntity<Employee> saveEmployee(Long businessId, Employee employee);

	ResponseEntity<Employee> updateEmployeeById(Long businessId, Long employeeId, Employee employee);

	ResponseEntity<Employee> deleteEmployeeById(Long businessId, Long employeeId);
}
