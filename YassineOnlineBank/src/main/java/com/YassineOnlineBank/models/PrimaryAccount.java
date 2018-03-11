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
@DiscriminatorValue("PA")
public class PrimaryAccount extends Account {

    @OneToMany(mappedBy = "primaryAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<PrimaryTransaction> primaryTransactionList;

    public List<PrimaryTransaction> getPrimaryTransactionList() {
        return primaryTransactionList;
    }

    public void setPrimaryTransactionList(List<PrimaryTransaction> primaryTransactionList) {
        this.primaryTransactionList = primaryTransactionList;
    }

	public PrimaryAccount(int accountNumber, BigDecimal accountBalance, Date creationDate,
			List<PrimaryTransaction> primaryTransactionList) {
		super(accountNumber, accountBalance, creationDate);
		this.primaryTransactionList = primaryTransactionList;
	}

	public PrimaryAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PrimaryAccount(int accountNumber, BigDecimal accountBalance, Date creationDate) {
		super(accountNumber, accountBalance, creationDate);
		// TODO Auto-generated constructor stub
	}



    

}



