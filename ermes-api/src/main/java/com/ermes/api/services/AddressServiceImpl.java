package com.ermes.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Customer;
import com.ermes.api.models.Address;

@Service
public class AddressServiceImpl implements AddressService
{
	@Autowired
	CustomerService customerService;

	@Override
	public ResponseEntity<Address> getAddress(Long businessId, Long customerId)
	{
		Customer customer = customerService.getCustomerById(businessId, customerId).getBody();
		Address address = customer.getAddress();

		if (address != null)
		{
			return new ResponseEntity<Address>(address, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Address>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Address> saveAddress(Long businessId, Long customerId, Address address)
	{
		try
		{
			Customer customer = customerService.getCustomerById(businessId, customerId).getBody();
			customer.setAddress(address);
			customerService.updateCustomerById(businessId, customerId, customer);

			return new ResponseEntity<Address>(HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Address>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Address> updateAddress(Long businessId, Long customerId, Address a)
	{
		Customer customer = customerService.getCustomerById(businessId, customerId).getBody();
		Address address = customer.getAddress();

		if (address != null)
		{
			customer.setAddress(a);
			customerService.updateCustomerById(businessId, customerId, customer);
			return new ResponseEntity<Address>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Address>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Address> deleteAddress(Long businessId, Long customerId)
	{
		Customer customer = customerService.getCustomerById(businessId, customerId).getBody();
		Address address = customer.getAddress();

		if (address != null)
		{
			customer.setAddress(null);
			customerService.updateCustomerById(businessId, customerId, customer);
			return new ResponseEntity<Address>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<Address>(HttpStatus.NOT_FOUND);
		}
	}
}
