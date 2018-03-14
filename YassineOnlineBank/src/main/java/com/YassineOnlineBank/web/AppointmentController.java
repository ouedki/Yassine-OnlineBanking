package com.YassineOnlineBank.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.YassineOnlineBank.models.Appointment;
import com.YassineOnlineBank.services.AppointmentService;

@Controller
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@GetMapping("/appointment-create")
	public String appointmentForm(Model m) {
		m.addAttribute("appointment", new Appointment());
		return"appointment";
	}
	
	@PostMapping("/appointment-create")
	public String createAppointment(@ModelAttribute(name="appointment")Appointment appointment,
			@ModelAttribute(name="dateString") String dateString, Model m, Principal p ) {
		appointmentService.createAppointment(appointment, dateString, p);
		m.addAttribute("appointment", new Appointment());
		return "redirect:/dashboard";
	}

}
