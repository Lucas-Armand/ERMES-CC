package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Appointment;
import com.ermes.api.services.AppointmentService;

@RestController
@RequestMapping("/businesses/{businessId}/appointments")
public class AppointmentController
{
	@Autowired
	private AppointmentService appointmentService;

	// GET /businesses/{businessId}/appointments
	// Retrieves the appointments of a business

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getAppointments(@PathVariable Long businessId)
	{
		return appointmentService.getAppointments(businessId);
	}

	// POST /businesses/{businessId}/appointments
	// Creates a new appointment for a business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createAppointment(@PathVariable Long businessId, @Validated @RequestBody Appointment appointment)
	{
		return appointmentService.saveAppointment(businessId, appointment);
	}

	// GET /businesses/{businessId}/appointments/{appointmentId}
	// Retrieves a specific appointment of business

	@RequestMapping(value = "/{appointmentId}")
	ResponseEntity<?> getAppointmentById(@PathVariable Long businessId, @PathVariable Long appointmentId)
	{
		return appointmentService.getAppointmentById(businessId, appointmentId);
	}

	// PUT /businesses/{businessId}/appointments/{appointmentId}
	// Updates a specific appointment of business

	@RequestMapping(value = "/{appointmentId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateAppointmentById(@PathVariable Long businessId, @PathVariable Long appointmentId,
			@Validated @RequestBody Appointment appointment)
	{
		return appointmentService.updateAppointmentById(businessId, appointmentId, appointment);
	}

	// DELETE /businesses/{businessId}/appointments/{appointmentId}
	// Deletes a specific appointment of a business

	@RequestMapping(value = "/{appointmentId}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteAppointmentById(@PathVariable Long businessId, @PathVariable Long appointmentId)
	{
		return appointmentService.deleteAppointmentById(businessId, appointmentId);
	}
}
