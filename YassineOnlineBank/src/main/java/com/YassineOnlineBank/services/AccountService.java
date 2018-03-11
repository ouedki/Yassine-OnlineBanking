package com.YassineOnlineBank.services;

import com.YassineOnlineBank.models.PrimaryAccount;
import com.YassineOnlineBank.models.SavingsAccount;

public interface AccountService {
	PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
//    void deposit(String accountType, double amount, Principal principal);
//    void withdraw(String accountType, double amount, Principal principal);
}
