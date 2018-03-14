package com.YassineOnlineBank.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.Recipient;
import com.YassineOnlineBank.models.SavingsTransaction;
import com.YassineOnlineBank.services.AccountService;

@Controller
public class TransferController {
	@Autowired
	private AccountService accountService;
	
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
	
	@GetMapping("/transfer-recipient")
	public String recipientForm(Model m, Principal p) {
		List<Recipient> recipientList = accountService.getRecipientList(p);
		m.addAttribute("recipient", new Recipient());
		m.addAttribute("recipientList", recipientList);
		return "recipient";
	}
	
	@PostMapping("/transfer-recipient")
	public String Saverecipient(Model m, Principal p, @ModelAttribute(name="recipient") Recipient recipient) {
		accountService.addRecipient(recipient, p);
		List<Recipient> recipientList = accountService.getRecipientList(p);
		m.addAttribute("recipientList", recipientList);
		m.addAttribute("recipient", new Recipient());
		return "recipient";
	}
	
	@GetMapping("/transfer-recipient-delete")
	public String deleteRecipient(@RequestParam(name="id") Long id, Model m, Principal p) {
		accountService.deleteRecipientById(id);
		List<Recipient> recipientList = accountService.getRecipientList(p);
		m.addAttribute("recipientList", recipientList);
		m.addAttribute("recipient", new Recipient());
		return "recipient";
	}
	
	@GetMapping("/transfer-recipient-edit")
	public String updaterecipientForm(@RequestParam(name="id") Long id, Model m, Principal p, @ModelAttribute(name="recipient") Recipient recipient) {
		List<Recipient> recipientList = accountService.getRecipientList(p);
		Recipient r = accountService.findRecipientById(id);
		m.addAttribute("recipient", r);
		m.addAttribute("recipientList", recipientList);
		return "recipient";
	}
	
	@GetMapping("/transfer-toSomeoneElse")
	public String transferToRecipientForm(Model m, Principal p) {
		List<Recipient> recipientList = accountService.getRecipientList(p);
		m.addAttribute("recipientList", recipientList);
		m.addAttribute("accountType", "");
		return "toSomeoneElse";
	}
	
	@PostMapping("/transfer-toSomeoneElse")
	public String transferToRecipient(@ModelAttribute(name="accountType") String accountType, 
			@ModelAttribute(name="recipientName") String recipientName, 
			@ModelAttribute(name="amount") String amount, 
			Principal p, PrimaryTransaction pt, SavingsTransaction st) {
		accountService.transferToRec(accountType, recipientName, Double.parseDouble(amount), pt, st, p);
		return "redirect:/dashboard";
	}
}
