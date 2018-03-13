package com.YassineOnlineBank.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.YassineOnlineBank.models.PrimaryAccount;
import com.YassineOnlineBank.models.SavingsAccount;
import com.YassineOnlineBank.models.User;
import com.YassineOnlineBank.services.UserService;

@Controller
@RequestMapping(value="/account", method=RequestMethod.GET)
public class AccountContoller {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/primaryAccount")
	public String getPA(Principal p, Model m) {
		User loggedInUser = userService.findByUsername(p.getName());
		PrimaryAccount pAcc = loggedInUser.getPrimaryAccount();
		m.addAttribute("primaryAccount", pAcc);
		
		return "primaryAccount";
	}
	
	@GetMapping("/savingsAccount")
	public String getSA(Principal p, Model m) {
		User loggedInUser = userService.findByUsername(p.getName());
		SavingsAccount sAcc = loggedInUser.getSavingsAccount();
		m.addAttribute("savingsAccount", sAcc);
		return "savingsAccount";
	}

}
