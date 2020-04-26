package com.home.model;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.home.repository.BanTransactionRepository;

@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
@Autowired
	private BanTransactionRepository bankTransactionRepository;
	@Override
	public void write(List<? extends BankTransaction> list) throws Exception {
		// TODO Auto-generated method stub
		bankTransactionRepository.saveAll(list);
	}

}
