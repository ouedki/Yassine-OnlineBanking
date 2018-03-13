package com.YassineOnlineBank.services;

import java.security.Principal;

import com.YassineOnlineBank.models.PrimaryAccount;
import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.SavingsAccount;
import com.YassineOnlineBank.models.SavingsTransaction;

public interface AccountService {
	PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);
    void transferBtwAcc(String acctTo, String acctFrom, double amount, PrimaryTransaction pt, SavingsTransaction st, Principal p);
}
