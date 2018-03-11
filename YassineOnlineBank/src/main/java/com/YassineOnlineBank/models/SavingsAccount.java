package com.YassineOnlineBank.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("SA")
public class SavingsAccount extends Account {

    @OneToMany(mappedBy = "savingsAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<SavingsTransaction> savingsTransactionList;

    public List<SavingsTransaction> getSavingsTransactionList() {
        return savingsTransactionList;
    }

    public void setSavingsTransactionList(List<SavingsTransaction> savingsTransactionList) {
        this.savingsTransactionList = savingsTransactionList;
    }

	public SavingsAccount(int accountNumber, BigDecimal accountBalance, Date creationDate,
			List<SavingsTransaction> savingsTransactionList) {
		super(accountNumber, accountBalance, creationDate);
		this.savingsTransactionList = savingsTransactionList;
	}

	public SavingsAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SavingsAccount(int accountNumber, BigDecimal accountBalance, Date creationDate) {
		super(accountNumber, accountBalance, creationDate);
		// TODO Auto-generated constructor stub
	}


}
