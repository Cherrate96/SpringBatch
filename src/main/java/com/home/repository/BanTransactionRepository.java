package com.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.home.model.BankTransaction;

public interface BanTransactionRepository extends JpaRepository<BankTransaction, Long>{
	

}
