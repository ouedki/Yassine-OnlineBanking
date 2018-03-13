package com.YassineOnlineBank.web;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.YassineOnlineBank.models.PrimaryAccount;
import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.SavingsAccount;
import com.YassineOnlineBank.models.SavingsTransaction;
import com.YassineOnlineBank.models.User;
import com.YassineOnlineBank.services.AccountService;
import com.YassineOnlineBank.services.TransactionService;
import com.YassineOnlineBank.services.UserService;

@Controller
public class AccountContoller {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping(value="/account-primaryAccount")
	public String getPA(Principal p, Model m) {
	
		List<PrimaryTransaction> primaryTransaction = transactionService.findPrimaryTransactionList(p.getName());
		User loggedInUser = userService.findByUsername(p.getName());
		
		PrimaryAccount pAcc = loggedInUser.getPrimaryAccount();
		m.addAttribute("primaryAccount", pAcc);
		m.addAttribute("primaryTransactionList", primaryTransaction);
		
		return "primaryAccount";
	}
	
	@GetMapping("/account-savingsAccount")
	public String getSA(Principal p, Model m) {
		List<SavingsTransaction> savingsTransaction = transactionService.findSavingsTransactionList(p.getName());
		User loggedInUser = userService.findByUsername(p.getName());
		
		SavingsAccount sAcc = loggedInUser.getSavingsAccount();
		m.addAttribute("savingsAccount", sAcc);
		m.addAttribute("savingsTransactionList", savingsTransaction);
		return "savingsAccount";
	}
	
	@GetMapping("/account-deposit")
	public String deposit(Model m) {
		m.addAttribute("accountType", "");
		m.addAttribute("amount", "");
		return "deposit";
	}
	
	@PostMapping("/account-deposit")
	public String saveDeposit(@ModelAttribute("amount") String amount, 
			@ModelAttribute("accountType") String accountType, Model m, Principal p) {
		accountService.deposit(accountType, Double.parseDouble(amount), p);
		return "redirect:/dashboard";
	}
	
	@GetMapping("/account-withdraw")
	public String withdraw(Model m) {
		m.addAttribute("accountType", "");
		m.addAttribute("amount", "");
		return "withdraw";
	}
	
	@PostMapping("/account-withdraw")
	public String saveWithdraw(@ModelAttribute("amount") String amount, 
			@ModelAttribute("accountType") String accountType, Model m, Principal p) {
		accountService.withdraw(accountType, Double.parseDouble(amount), p);
		return "redirect:/dashboard";
	}

}
