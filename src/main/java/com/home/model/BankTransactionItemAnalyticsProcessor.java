package com.home.model;

import java.text.SimpleDateFormat;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionItemAnalyticsProcessor implements ItemProcessor<BankTransaction,BankTransaction > 
{

	private double totalDebit;
	private double totalCredit;
	
	
	public double getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(double totalDebit) {
		this.totalDebit = totalDebit;
	}
	public double getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(double totalCredit) {
		this.totalCredit = totalCredit;
	}
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	private SimpleDateFormat dateFormat=new SimpleDateFormat("dd/mm/yyyy-HH:mm");


	@Override
	public BankTransaction process(BankTransaction bankTransaction) throws Exception {
		if(bankTransaction.getTransactionType().equals("D")) 
			totalDebit+=bankTransaction.getAmount();
		else if(bankTransaction.getTransactionType().equals("C")) 
			totalCredit+=bankTransaction.getAmount();
		return bankTransaction;
	}



}
