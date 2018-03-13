package com.YassineOnlineBank.services;

import java.util.List;

import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.SavingsTransaction;

public interface TransactionService {
	
	List<PrimaryTransaction> findPrimaryTransactionList(String username);
	List<SavingsTransaction> findSavingsTransactionList(String username);
	void savePrimaryDepositTransaction (PrimaryTransaction pt);
	void saveSavingsDepositTransaction (SavingsTransaction st);
	void savePrimaryWithdrawTransaction(PrimaryTransaction pt);
	void saveSavingsWithdrawTransaction(SavingsTransaction st);

}
