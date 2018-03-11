package com.YassineOnlineBank.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.YassineOnlineBank.models.SavingsTransaction;


public interface SavingsTransactionDao extends CrudRepository<SavingsTransaction, Long> {

    List<SavingsTransaction> findAll();
}

