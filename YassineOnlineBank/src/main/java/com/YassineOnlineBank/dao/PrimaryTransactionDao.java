package com.YassineOnlineBank.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.YassineOnlineBank.models.PrimaryTransaction;

public interface PrimaryTransactionDao extends CrudRepository<PrimaryTransaction, Long> {

    List<PrimaryTransaction> findAll();
}
