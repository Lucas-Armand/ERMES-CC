package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Business;
import com.ermes.api.services.BusinessService;

@RestController
@RequestMapping("/businesses")
public class BusinessController
{
	@Autowired
	private BusinessService businessService;

	// GET /businesses
	// Retrieves a list of businesses

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getBusinesses()
	{
		return businessService.getBusinesses();
	}

	// POST /businesses
	// Creates a new business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createBusiness(@Validated @RequestBody Business business)
	{
		return businessService.saveBusiness(business);
	}

	// GET /businesses/{businessId}
	// Retrieves a specific business

	@RequestMapping(value = "/{businessId}")
	ResponseEntity<?> getBusinessById(@PathVariable Long businessId)
	{
		return businessService.getBusinessById(businessId);
	}

	// PUT /businesses/{businessId}
	// Updates a specific business

	@RequestMapping(value = "/{businessId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateBusinessById(@PathVariable Long businessId, @Validated @RequestBody Business business)
	{
		return businessService.updateBusinessById(businessId, business);
	}

	// DELETE /businesses/{businessId}
	// Deletes a specific business

	/*
	 * @RequestMapping(value = "/{businessId}", method = RequestMethod.DELETE)
	 * ResponseEntity<?> deleteBusinessById(@PathVariable Long businessId) {
	 * businessService.deleteBusiness(businessId); return new
	 * ResponseEntity<Business>(HttpStatus.NO_CONTENT); }
	 */
}