package com.YassineOnlineBank.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@RequestMapping("/account")
public class AccountContoller {
	
	@GetMapping
	public String getPA() {
		return "primaryAccount";
	}
	
	@GetMapping
	public String getSA() {
		return "savingsAccount";
	}

}
