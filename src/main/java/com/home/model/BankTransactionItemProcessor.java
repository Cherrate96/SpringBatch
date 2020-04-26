package com.home.model;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

//@Component
public class BankTransactionItemProcessor implements ItemProcessor<BankTransaction,BankTransaction > {

	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd/mm/yyyy-HH:mm");
	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws Exception {
		// TODO Auto-generated method stub
		bankTransaction.setTransactionDate(dateFormat.parse(bankTransaction.getStrTransactionDate()));
		return bankTransaction;
	}



}
