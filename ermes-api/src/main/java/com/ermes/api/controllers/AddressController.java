package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Address;
import com.ermes.api.services.AddressService;

@RestController
@RequestMapping("/businesses/{businessId}/customers/{customerId}/address")
public class AddressController
{
	@Autowired
	private AddressService addressService;

	// GET /businesses/{businessId}/customers/{customerId}/address
	// Retrieves the addresses of a business

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getAddresss(@PathVariable Long businessId, @PathVariable Long customerId)
	{
		return addressService.getAddress(businessId, customerId);
	}

	// POST /businesses/{businessId}/customers/{customerId}/address
	// Creates a new address for a business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createAddress(@PathVariable Long businessId, @PathVariable Long customerId,
			@Validated @RequestBody Address address)
	{
		return addressService.saveAddress(businessId, customerId, address);
	}

	// PUT /businesses/{businessId}/customers/{customerId}/address
	// Updates a specific address of business

	@RequestMapping(method = RequestMethod.PUT)
	ResponseEntity<?> updateAddressById(@PathVariable Long businessId, @PathVariable Long customerId,
			@Validated @RequestBody Address address)
	{
		return addressService.updateAddress(businessId, customerId, address);
	}

	// DELETE /businesses/{businessId}/customers/{customerId}/address
	// Deletes a specific address of a business

	@RequestMapping(method = RequestMethod.DELETE)
	ResponseEntity<?> deleteAddressById(@PathVariable Long businessId, @PathVariable Long customerId)
	{
		return addressService.deleteAddress(businessId, customerId);
	}
}
