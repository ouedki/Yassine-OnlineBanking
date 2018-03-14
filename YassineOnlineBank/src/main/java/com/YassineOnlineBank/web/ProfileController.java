package com.YassineOnlineBank.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.YassineOnlineBank.models.User;
import com.YassineOnlineBank.services.UserService;

@Controller
public class ProfileController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/user-profile")
	public String profileForm(Model m, Principal p) {
		User user = userService.findByUsername(p.getName());
		m.addAttribute("user", user);
		return "profile";
	}
	
	@PostMapping("/user-profile")
	public String updateProfile(@ModelAttribute(name="user") User u, Model m) {
		User user = userService.findByUsername(u.getUsername());
		user.setFirstName(u.getFirstName());
		user.setLastName(u.getLastName());
		user.setEmail(u.getEmail());
		user.setPhone(u.getPhone());
		user.setUsername(u.getUsername());
		userService.saveUser(user);
		m.addAttribute("user", user);
		return "profile";
	}

}
