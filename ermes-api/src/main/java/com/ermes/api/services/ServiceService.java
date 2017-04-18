package com.ermes.api.services;

import org.springframework.http.ResponseEntity;

import com.ermes.api.models.Service;

public interface ServiceService
{
	ResponseEntity<?> getServices(Long businessId);
	
	ResponseEntity<?> getServiceById(Long businessId, Long serviceId);

	ResponseEntity<?> saveService(Long businessId, Service service);
	
	ResponseEntity<?> updateServiceById(Long businessId, Long serviceId, Service s);

	ResponseEntity<?> deleteServiceById(Long businessId, Long serviceId);
}
