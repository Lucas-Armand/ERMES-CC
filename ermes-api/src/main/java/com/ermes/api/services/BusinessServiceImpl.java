package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Business;
import com.ermes.api.repositories.BusinessRepository;

@Service
public class BusinessServiceImpl implements BusinessService
{

	@Autowired
	BusinessRepository businessRepository;
	
	/*@Override
	public void updateBusinesssById(Long id, Business b)
	{
		Business business = businessRepository.findOne(id);
		business.setName(b.getName());
		business.setOpeningTime(b.getOpeningTime());
		business.setClosingTime(b.getClosingTime());
		business.setFieldOfActivity(b.getFieldOfActivity());
		businessRepository.save(business);
	}*/

	@Override
	public ResponseEntity<List<Business>> getBusinesses()
	{
		List<Business> businesses = (List<Business>) businessRepository.findAll();
		
		if (!businesses.isEmpty())
		{
			return new ResponseEntity<List<Business>>(businesses, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Business>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Business> getBusinessById(Long businessId)
	{
		Business business = businessRepository.findOne(businessId);
		
		if (business != null)
		{
			return new ResponseEntity<Business>(business, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Business> saveBusiness(Business business)
	{
		try
		{
			businessRepository.save(business);
			return new ResponseEntity<Business>(business, HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Business>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Business> updateBusinessById(Long businessId, Business b)
	{
		Business business = getBusinessById(businessId).getBody();
		
		if (business != null)
		{
			business.setName(b.getName());
			business.setOpeningTime(b.getOpeningTime());
			business.setClosingTime(b.getClosingTime());
			business.setFieldOfActivity(b.getFieldOfActivity());
			businessRepository.save(business);
			
			return new ResponseEntity<Business>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Business>(HttpStatus.NOT_FOUND);
		}
	}
}
