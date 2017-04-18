package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Customer;
import com.ermes.api.services.CustomerService;

@RestController
@RequestMapping("/businesses/{businessId}/customers")
public class CustomerController
{
	@Autowired
	private CustomerService customerService;

	// GET /businesses/{businessId}/customers
	// Retrieves the customers of a business

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getCustomers(@PathVariable Long businessId)
	{
		return customerService.getCustomers(businessId);
	}

	// POST /businesses/{businessId}/customers
	// Creates a new customer for a business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createCustomer(@PathVariable Long businessId, @Validated @RequestBody Customer customer)
	{
		return customerService.saveCustomer(businessId, customer);
	}

	// GET /businesses/{businessId}/customers/{customerId}
	// Retrieves a specific customer of business

	@RequestMapping(value = "/{customerId}")
	ResponseEntity<?> getCustomerById(@PathVariable Long businessId, @PathVariable Long customerId)
	{
		return customerService.getCustomerById(businessId, customerId);
	}

	// PUT /businesses/{businessId}/customers/{customerId}
	// Updates a specific customer of business

	@RequestMapping(value = "/{customerId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateCustomerById(@PathVariable Long businessId, @PathVariable Long customerId,
			@Validated @RequestBody Customer customer)
	{
		return customerService.updateCustomerById(businessId, customerId, customer);
	}

	// DELETE /businesses/{businessId}/customers/{customerId}
	// Deletes a specific customer of a business

	@RequestMapping(value = "/{customerId}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteCustomerById(@PathVariable Long businessId, @PathVariable Long customerId)
	{
		return customerService.deleteCustomerById(businessId, customerId);
	}
}
