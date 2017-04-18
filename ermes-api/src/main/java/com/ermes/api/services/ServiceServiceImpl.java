package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.ermes.api.models.Business;
import com.ermes.api.models.Service;

@Component
public class ServiceServiceImpl implements ServiceService
{
	@Autowired
	BusinessService businessService;

	@Override
	public ResponseEntity<List<Service>> getServices(Long businessId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		List<Service> services = business.getServices();

		if (!services.isEmpty())
		{
			return new ResponseEntity<List<Service>>(services, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Service>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Service> getServiceById(Long businessId, Long serviceId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();

		for (Service service : business.getServices())
		{
			if (service.getId() == serviceId)
			{
				return new ResponseEntity<Service>(service, HttpStatus.OK);
			}
		}

		return new ResponseEntity<Service>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Service> saveService(Long businessId, Service service)
	{
		try
		{
			Business business = businessService.getBusinessById(businessId).getBody();
			business.getServices().add(service);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Service>(HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Service>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Service> updateServiceById(Long businessId, Long serviceId, Service s)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Service service = getServiceById(businessId, serviceId).getBody();

		if (service != null)
		{
			int index = business.getServices().indexOf(service);
			s.setId(serviceId);
			business.getServices().set(index, s);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Service>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Service>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Service> deleteServiceById(Long businessId, Long serviceId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Service service = getServiceById(businessId, serviceId).getBody();

		if (service != null)
		{
			business.getServices().remove(service);
			businessService.updateBusinessById(businessId, business);
			return new ResponseEntity<Service>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<Service>(HttpStatus.NOT_FOUND);
		}
	}
}
