package com.YassineOnlineBank.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.YassineOnlineBank.dao.PrimaryTransactionDao;
import com.YassineOnlineBank.dao.SavingsTransactionDao;
import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.SavingsTransaction;
import com.YassineOnlineBank.models.User;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private UserService userService;
	@Autowired
	private PrimaryTransactionDao primaryTransactionDao;
	
	@Autowired
	private SavingsTransactionDao savingsTransactionDao;

	@Override
	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUsername(username);
        List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
        return primaryTransactionList;

	}

	@Override
	public List<SavingsTransaction> findSavingsTransactionList(String username) {
		User user = userService.findByUsername(username);
        List<SavingsTransaction> savingsTransactionList = user.getSavingsAccount().getSavingsTransactionList();
        return savingsTransactionList;
	}

	@Override
	public void savePrimaryDepositTransaction(PrimaryTransaction pt) {
		primaryTransactionDao.save(pt);
		
	}

	@Override
	public void saveSavingsDepositTransaction(SavingsTransaction st) {
		savingsTransactionDao.save(st);
		
	}

}
