package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Business;
import com.ermes.api.models.Customer;

@Service
public class CustomerServiceImpl implements CustomerService
{	
	@Autowired
	BusinessService businessService;

	@Override
	public ResponseEntity<List<Customer>> getCustomers(Long businessId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		List<Customer> customers = business.getCustomers();
		
		if (!customers.isEmpty())
		{
			return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Customer>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Customer> getCustomerById(Long businessId, Long customerId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();

		for (Customer customer : business.getCustomers())
		{
			if (customer.getId() == customerId)
			{
				return new ResponseEntity<Customer>(customer, HttpStatus.OK);
			}
		}

		return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Customer> saveCustomer(Long businessId, Customer customer)
	{
		try
		{
			Business business = businessService.getBusinessById(businessId).getBody();
			business.getCustomers().add(customer);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Customer>(HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Customer> updateCustomerById(Long businessId, Long customerId, Customer c)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Customer customer = getCustomerById(businessId, customerId).getBody();

		if (customer != null)
		{
			int index = business.getCustomers().indexOf(customer);
			c.setId(customerId);
			business.getCustomers().set(index, c);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Customer>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Customer> deleteCustomerById(Long businessId, Long customerId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Customer customer = getCustomerById(businessId, customerId).getBody();

		if (customer != null)
		{
			business.getCustomers().remove(customer);
			businessService.updateBusinessById(businessId, business);
			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
		}
	}
}
