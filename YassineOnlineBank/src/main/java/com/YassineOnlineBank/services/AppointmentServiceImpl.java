package com.YassineOnlineBank.services;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YassineOnlineBank.dao.AppointmentDao;
import com.YassineOnlineBank.models.Appointment;
import com.YassineOnlineBank.models.User;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	
	@Autowired
	private AppointmentDao appointmentDao;
	
	@Autowired 
	private UserService userService;
	
	@Override
	public Appointment createAppointment(Appointment appointment, String date,Principal p) {
		User user = userService.findByUsername(p.getName());
		try {
			SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			Date d = fd.parse(date);
			appointment.setDate(d);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		appointment.setUser(user);
		
		return appointmentDao.save(appointment);
	}
	
	@Override
	public Appointment findAppointmentById(Long id) {
		return null;
	}
}
