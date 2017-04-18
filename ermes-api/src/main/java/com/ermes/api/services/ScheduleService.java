package com.ermes.api.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ermes.api.models.Schedule;

public interface ScheduleService
{
	ResponseEntity<List<Schedule>> getSchedules(Long businessId, Long employeeId);

	ResponseEntity<Schedule> getScheduleById(Long businessId, Long employeeId, Long scheduleId);

	ResponseEntity<Schedule> saveSchedule(Long businessId, Long employeeId, Schedule schedule);

	ResponseEntity<Schedule> updateScheduleById(Long businessId, Long employeeId, Long scheduleId, Schedule schedule);

	ResponseEntity<Schedule> deleteScheduleById(Long businessId, Long employeeId, Long scheduleId);
}
