package com.ermes.api.services;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ermes.api.models.Appointment;

public interface AppointmentService
{
	ResponseEntity<List<Appointment>> getAppointments(Long businessId);

	ResponseEntity<Appointment> getAppointmentById(Long businessId, Long appointmentId);

	ResponseEntity<Appointment> saveAppointment(Long businessId, Appointment appointment);

	ResponseEntity<Appointment> updateAppointmentById(Long businessId, Long appointmentId, Appointment appointment);

	ResponseEntity<Appointment> deleteAppointmentById(Long businessId, Long appointmentId);
}
