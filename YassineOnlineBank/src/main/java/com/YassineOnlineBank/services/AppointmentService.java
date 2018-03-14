package com.YassineOnlineBank.services;

import java.security.Principal;

import com.YassineOnlineBank.models.Appointment;

public interface AppointmentService {

	Appointment createAppointment(Appointment appointment, String date, Principal p);
	Appointment findAppointmentById(Long id);
}
