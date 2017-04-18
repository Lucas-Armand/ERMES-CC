package com.ermes.api.services;

import org.springframework.http.ResponseEntity;

import com.ermes.api.models.Address;

public interface AddressService
{
	ResponseEntity<Address> getAddress(Long businessId, Long customerId);

	ResponseEntity<Address> saveAddress(Long businessId, Long customerId, Address address);
	
	ResponseEntity<Address> updateAddress(Long businessId, Long customerId, Address address);

	ResponseEntity<Address> deleteAddress(Long businessId, Long customerId);
}
