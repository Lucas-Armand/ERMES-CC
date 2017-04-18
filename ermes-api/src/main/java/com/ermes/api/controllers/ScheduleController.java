package com.ermes.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ermes.api.models.Schedule;
import com.ermes.api.services.ScheduleService;

@RestController
@RequestMapping("/businesses/{businessId}/employees/{employeesId}/schedules")
public class ScheduleController
{
	@Autowired
	private ScheduleService scheduleService;

	// GET /businesses/{businessId}/employees/{employeesId}/schedules
	// Retrieves the schedules of a business

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<?> getSchedules(@PathVariable Long businessId, @PathVariable Long employeesId)
	{
		return scheduleService.getSchedules(businessId, employeesId);
	}

	// POST /businesses/{businessId}/employees/{employeesId}/schedules
	// Creates a new schedule for a business

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createSchedule(@PathVariable Long businessId, @PathVariable Long employeesId,
			@Validated @RequestBody Schedule schedule)
	{
		return scheduleService.saveSchedule(businessId, employeesId, schedule);
	}

	// GET /businesses/{businessId}/employees/{employeesId}/schedules/{scheduleId}
	// Retrieves a specific schedule of business

	@RequestMapping(value = "/{scheduleId}")
	ResponseEntity<?> getScheduleById(@PathVariable Long businessId, @PathVariable Long employeesId,
			@PathVariable Long scheduleId)
	{
		return scheduleService.getScheduleById(businessId, employeesId, scheduleId);
	}

	// PUT /businesses/{businessId}/employees/{employeesId}/schedules/{scheduleId}
	// Updates a specific schedule of business

	@RequestMapping(value = "/{scheduleId}", method = RequestMethod.PUT)
	ResponseEntity<?> updateScheduleById(@PathVariable Long businessId, @PathVariable Long employeesId,
			@PathVariable Long scheduleId, @Validated @RequestBody Schedule schedule)
	{
		return scheduleService.updateScheduleById(businessId, employeesId, scheduleId, schedule);
	}
	
	// DELETE /businesses/{businessId}/employees/{employeesId}/schedules/{scheduleId}
	// Deletes a specific schedule of a business

	@RequestMapping(value = "/{scheduleId}", method = RequestMethod.DELETE)
	ResponseEntity<?> deleteScheduleById(@PathVariable Long businessId, @PathVariable Long employeesId,
			@PathVariable Long scheduleId)
	{
		return scheduleService.deleteScheduleById(businessId, employeesId, scheduleId);
	}
}
