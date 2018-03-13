package com.YassineOnlineBank.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.SavingsTransaction;
import com.YassineOnlineBank.services.AccountService;

@Controller
public class TransferController {
	@Autowired
	AccountService accountService;
	
	@GetMapping("/transfer-betweenAccounts")
	public String betweenAcc(Model m) {
		m.addAttribute("transferTo", "");
		m.addAttribute("transferFrom", "");
		m.addAttribute("amount", "");
		return "betweenAccounts";
	}
	
	@PostMapping("/transfer-betweenAccounts")
	public String transferBtwAccts(@ModelAttribute(name="transferTo") String acctTo, 
			@ModelAttribute(name="transferFrom") String acctFrom, 
			@ModelAttribute(name="amount") String amount,
			PrimaryTransaction pt, SavingsTransaction st, Principal p) {
			accountService.transferBtwAcc(acctTo, acctFrom, Double.parseDouble(amount), pt, st, p);
		return "redirect:/dashboard";
	}

}
