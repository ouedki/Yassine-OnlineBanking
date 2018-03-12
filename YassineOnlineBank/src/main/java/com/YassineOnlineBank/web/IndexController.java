package com.YassineOnlineBank.web;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.YassineOnlineBank.dao.RoleDao;
import com.YassineOnlineBank.models.PrimaryAccount;
import com.YassineOnlineBank.models.SavingsAccount;
import com.YassineOnlineBank.models.User;
import com.YassineOnlineBank.models.UserRole;
import com.YassineOnlineBank.services.UserService;

@Controller
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleDao roleDao;

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	@GetMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signUp(@ModelAttribute("user") User user, Model model) {
		if(userService.checkUserExists(user.getUsername(), user.getEmail())) {
			if(userService.checkByUsername(user.getUsername())) {
				model.addAttribute("usernameExists", true);
			}if(userService.checkByEmail(user.getEmail())) {
				model.addAttribute("emailExists", true);
			}
			return "signup";
		}else {
			Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName("ROLE_USER")));
			userService.createUser(user, userRoles );
			return "redirect:/index";
		}
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Principal p, Model model) {
		User loggedInUser = userService.findByUsername(p.getName());
		PrimaryAccount pAcc = loggedInUser.getPrimaryAccount();
		SavingsAccount sAcc = loggedInUser.getSavingsAccount();
		model.addAttribute("primaryAccount", pAcc);
		model.addAttribute("savingsAccount", sAcc);
		return "dashboard";
	}
}
