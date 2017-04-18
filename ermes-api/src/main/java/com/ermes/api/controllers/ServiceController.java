package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Service;
import com.ermes.api.services.ServiceService;

@RestController
@RequestMapping("/businesses/{businessId}/services")
public class ServiceController
{
	@Autowired
	private ServiceService serviceService;

	// GET /businesses/{businessId}/services
	// Retrieves the services of a business

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getServices(@PathVariable Long businessId)
	{
		return serviceService.getServices(businessId);
	}

	// POST /businesses/{businessId}/services
	// Creates a new service for a business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createService(@PathVariable Long businessId, @Validated @RequestBody Service service)
	{
		return serviceService.saveService(businessId, service);
	}

	// GET /businesses/{businessId}/services/{serviceId}
	// Retrieves a specific service of business

	@RequestMapping(value = "/{serviceId}")
	ResponseEntity<?> getServiceById(@PathVariable Long businessId, @PathVariable Long serviceId)
	{
		return serviceService.getServiceById(businessId, serviceId);
	}

	// PUT /businesses/{businessId}/services/{serviceId}
	// Updates a specific service of business

	@RequestMapping(value = "/{serviceId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateServiceById(@PathVariable Long businessId, @PathVariable Long serviceId,
			@Validated @RequestBody Service service)
	{
		return serviceService.updateServiceById(businessId, serviceId, service);
	}

	// DELETE /businesses/{businessId}/services/{index}
	// Deletes a specific service of a business

	@RequestMapping(value = "/{serviceId}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteServiceById(@PathVariable Long businessId, @PathVariable Long serviceId)
	{
		return serviceService.deleteServiceById(businessId, serviceId);
	}
}
