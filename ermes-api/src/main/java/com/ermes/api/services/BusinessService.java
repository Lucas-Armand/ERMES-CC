package com.ermes.api.services;

import com.ermes.api.models.Business;
import java.util.List;

import org.springframework.http.ResponseEntity;

public interface BusinessService
{
	ResponseEntity<List<Business>> getBusinesses();

	ResponseEntity<Business> getBusinessById(Long businessId);

	ResponseEntity<Business> saveBusiness(Business business);
	
	ResponseEntity<Business> updateBusinessById(Long businessId, Business business);

	//ResponseEntity<Business> deleteBusiness(Long businessId);
}
