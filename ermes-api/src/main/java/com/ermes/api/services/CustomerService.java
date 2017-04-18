package com.ermes.api.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ermes.api.models.Customer;

public interface CustomerService
{
	ResponseEntity<List<Customer>> getCustomers(Long businessId);

	ResponseEntity<Customer> getCustomerById(Long businessId, Long customerId);

	ResponseEntity<Customer> saveCustomer(Long businessId, Customer customer);

	ResponseEntity<Customer> updateCustomerById(Long businessId, Long customerId, Customer customer);

	ResponseEntity<Customer> deleteCustomerById(Long businessId, Long customerId);
}
