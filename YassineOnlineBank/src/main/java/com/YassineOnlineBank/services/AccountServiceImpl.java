package com.YassineOnlineBank.services;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.YassineOnlineBank.dao.AccountDao;
import com.YassineOnlineBank.dao.RecipientDao;
import com.YassineOnlineBank.models.PrimaryAccount;
import com.YassineOnlineBank.models.PrimaryTransaction;
import com.YassineOnlineBank.models.Recipient;
import com.YassineOnlineBank.models.SavingsAccount;
import com.YassineOnlineBank.models.SavingsTransaction;
import com.YassineOnlineBank.models.User;



@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	private static int nextAccountNumber = 11111111;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RecipientDao recipientDao;

    @Autowired
    private UserService userService;
    
    @Autowired
    private TransactionService transactionService;

    public PrimaryAccount createPrimaryAccount() {
        PrimaryAccount primaryAccount = new PrimaryAccount();
        primaryAccount.setAccountBalance(new BigDecimal(0.0));
        primaryAccount.setAccountNumber(accountGen());
        primaryAccount.setCreationDate(new Date());

        accountDao.save(primaryAccount);

        return (PrimaryAccount) accountDao.findByAccountNumber(primaryAccount.getAccountNumber());
    }

    public SavingsAccount createSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountBalance(new BigDecimal(0.0));
        savingsAccount.setAccountNumber(accountGen());
        savingsAccount.setCreationDate(new Date());

        accountDao.save(savingsAccount);

        return (SavingsAccount) accountDao.findByAccountNumber(savingsAccount.getAccountNumber());
    }
    
    public void deposit(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
            accountDao.save(primaryAccount);

            Date date = new Date();

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Deposit to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryDepositTransaction(primaryTransaction);
            
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            accountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Deposit to savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
        }
    }
    
    public void withdraw(String accountType, double amount, Principal principal) {
        User user = userService.findByUsername(principal.getName());

        if (accountType.equalsIgnoreCase("Primary")) {
            PrimaryAccount primaryAccount = user.getPrimaryAccount();
            primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            accountDao.save(primaryAccount);

            Date date = new Date();

            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Withdraw from Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccount savingsAccount = user.getSavingsAccount();
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            accountDao.save(savingsAccount);

            Date date = new Date();
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Withdraw from savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
        }
    }

	@Override
	public void transferBtwAcc(String acctTo, String acctFrom, double amount, PrimaryTransaction pt,
			SavingsTransaction st, Principal p) {
		User user = userService.findByUsername(p.getName());
		if (acctTo.equalsIgnoreCase("Primary") && acctFrom.equalsIgnoreCase("Savings")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().add(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			accountDao.save(primaryAccount);
			accountDao.save(savingsAccount);

            Date date = new Date();
            
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer from Savings to Primary Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer from Savings to Primary Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
		} else if (acctFrom.equalsIgnoreCase("Primary") && acctTo.equalsIgnoreCase("Savings")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			SavingsAccount savingsAccount = user.getSavingsAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
			accountDao.save(primaryAccount);
			accountDao.save(savingsAccount);

            Date date = new Date();
            
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer from Primary to Savings Account", "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer from Primary to Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
            transactionService.saveSavingsDepositTransaction(savingsTransaction);
		}
	}
	
	@Override
	public Recipient addRecipient(Recipient recipient, Principal p) {
		User user = userService.findByUsername(p.getName());
		recipient.setUser(user);
		Recipient newRecipient = recipientDao.save(recipient);
		return newRecipient ;
	}
	
	@Override
	public void deleteRecipientById(Long id) {
        recipientDao.delete(id);
    }
	
	@Override
	public Recipient findRecipientById(Long id) {
        return recipientDao.findOne(id);
    }
	
	@Override
	public List<Recipient> getRecipientList(Principal p) {
		User user = userService.findByUsername(p.getName());
		List<Recipient> recipientList = user.getRecipientList();
        return recipientList;
    }
	
	@Override
	public void transferToRec(String accountType, String recipientName, double amount,PrimaryTransaction pt, SavingsTransaction st, Principal p) {
		User user = userService.findByUsername(p.getName());
		if (accountType.equals("Primary")) {
			PrimaryAccount primaryAccount = user.getPrimaryAccount();
			PrimaryAccount recipientAcc = userService.findByUsername(recipientName).getPrimaryAccount();
			primaryAccount.setAccountBalance(primaryAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			recipientAcc.setAccountBalance(recipientAcc.getAccountBalance().add(new BigDecimal(amount)));
			accountDao.save(primaryAccount);
			accountDao.save(recipientAcc);
			
			Date date = new Date();
            
            PrimaryTransaction primaryTransaction = new PrimaryTransaction(date, "Transfer from Primary to " + recipientName, "Account", "Finished", amount, primaryAccount.getAccountBalance(), primaryAccount);
            PrimaryTransaction recipientPrimaryTransaction = new PrimaryTransaction(date, "Transfer from " + user.getFirstName() , "Account", "Finished", amount, recipientAcc.getAccountBalance(), recipientAcc);
            transactionService.savePrimaryWithdrawTransaction(primaryTransaction);
            transactionService.savePrimaryDepositTransaction(recipientPrimaryTransaction);
            
		} else if (accountType.equals("Savings")) {
			SavingsAccount savingsAccount = user.getSavingsAccount();
			PrimaryAccount recipientAcc = userService.findByUsername(recipientName).getPrimaryAccount();
			savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
			recipientAcc.setAccountBalance(recipientAcc.getAccountBalance().add(new BigDecimal(amount)));
			accountDao.save(savingsAccount);
			accountDao.save(recipientAcc);
			
			Date date = new Date();
            
            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer from savings to " + recipientName, "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
            PrimaryTransaction recipientPrimaryTransaction = new PrimaryTransaction(date, "Transfer from " + user.getFirstName() , "Account", "Finished", amount, recipientAcc.getAccountBalance(), recipientAcc);
            transactionService.saveSavingsWithdrawTransaction(savingsTransaction);
            transactionService.savePrimaryDepositTransaction(recipientPrimaryTransaction);
		}
	}
	
	private int accountGen() {
        return ++nextAccountNumber;
    }

	

}
