package com.YassineOnlineBank.dao;

import org.springframework.data.repository.CrudRepository;

import com.YassineOnlineBank.models.Account;

public interface AccountDao extends CrudRepository<Account, Long>{
	Account findByAccountNumber (int accountNumber);

}
