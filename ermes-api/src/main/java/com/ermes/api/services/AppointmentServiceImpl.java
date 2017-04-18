package com.ermes.api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ermes.api.models.Appointment;
import com.ermes.api.models.Business;

@Service
public class AppointmentServiceImpl implements AppointmentService
{	
	@Autowired
	BusinessService businessService;

	@Override
	public ResponseEntity<List<Appointment>> getAppointments(Long businessId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		List<Appointment> appointments = business.getAppointments();
		
		if (!appointments.isEmpty())
		{
			return new ResponseEntity<List<Appointment>>(appointments, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Appointment>>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Appointment> getAppointmentById(Long businessId, Long appointmentId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();

		for (Appointment appointment : business.getAppointments())
		{
			if (appointment.getId() == appointmentId)
			{
				return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
			}
		}

		return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
	}

	@Override
	public ResponseEntity<Appointment> saveAppointment(Long businessId, Appointment appointment)
	{
		try
		{
			Business business = businessService.getBusinessById(businessId).getBody();
			business.getAppointments().add(appointment);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Appointment>(HttpStatus.CREATED);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Appointment>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Appointment> updateAppointmentById(Long businessId, Long appointmentId, Appointment a)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Appointment appointment = getAppointmentById(businessId, appointmentId).getBody();

		if (appointment != null)
		{
			int index = business.getAppointments().indexOf(appointment);
			a.setId(appointmentId);
			business.getAppointments().set(index, a);
			businessService.updateBusinessById(businessId, business);

			return new ResponseEntity<Appointment>(HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Appointment> deleteAppointmentById(Long businessId, Long appointmentId)
	{
		Business business = businessService.getBusinessById(businessId).getBody();
		Appointment appointment = getAppointmentById(businessId, appointmentId).getBody();

		if (appointment != null)
		{
			business.getAppointments().remove(appointment);
			businessService.updateBusinessById(businessId, business);
			return new ResponseEntity<Appointment>(HttpStatus.NO_CONTENT);
		}
		else
		{
			return new ResponseEntity<Appointment>(HttpStatus.NOT_FOUND);
		}
	}
}
