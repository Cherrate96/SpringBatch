package com.home.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.home.model.BankTransaction;
import com.home.model.BankTransactionItemProcessor;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	@Autowired
	 private JobBuilderFactory jobBuilderFactory;
	@Autowired  
	private StepBuilderFactory stepBuilderFactory;
	@Autowired 
	private ItemReader<BankTransaction> bankTransactionItemReader;
	@Autowired 
	private ItemWriter<BankTransaction> bankTransactionItemWriter;
 
	//private ItemProcessor<BankTransaction,BankTransaction> bankTransactionItemProcessor;
	
	
	@Bean
	public Job bankjob()
	{
		Step step1=stepBuilderFactory.get("step-load-data")
				.<BankTransaction,BankTransaction>chunk(100).reader(bankTransactionItemReader)
.processor(compositeItemProcessor()).writer(bankTransactionItemWriter)
.build();

		return jobBuilderFactory.get("bank-data-loader-job").start(step1).build();
	}
	
	
	@Bean
	public ItemProcessor<BankTransaction,BankTransaction> compositeItemProcessor() {
		List<ItemProcessor<BankTransaction, BankTransaction>> itemProcessors=new ArrayList();
		itemProcessors.add(itemProcessor1());
		itemProcessors.add(itemProcessor1());
		CompositeItemProcessor<BankTransaction, BankTransaction> compositeItemProcessor=new
				CompositeItemProcessor<BankTransaction, BankTransaction>();
		compositeItemProcessor.setDelegates(itemProcessors);
		
		return compositeItemProcessor;
	}
	@Bean 
	BankTransactionItemProcessor itemProcessor1(){
		return new BankTransactionItemProcessor();
	}
	@Bean 
	BankTransactionItemProcessor itemProcessor2(){
		return new BankTransactionItemProcessor();
	}
	
	@Bean
	public FlatFileItemReader<BankTransaction> fileItemReader(@Value("${inputFile}") Resource inputFile)
	{
		FlatFileItemReader<BankTransaction> fileItemReader=new FlatFileItemReader<BankTransaction>();
		fileItemReader.setName("FFIR1");
		//la ligne qu'il faut sauter(les meta data)
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setResource(inputFile);
		fileItemReader.setLineMapper(lineMapper());
		return fileItemReader;
	}
	@Bean
	public LineMapper<BankTransaction> lineMapper() {
		DefaultLineMapper<BankTransaction> lineMapper=new DefaultLineMapper<BankTransaction>();
		DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
		//spécifier le séparateur 
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id","accountID","strTransactionDate","transactionType","amount");
		lineMapper.setLineTokenizer(lineTokenizer);
		BeanWrapperFieldSetMapper fieldSetMapper=new BeanWrapperFieldSetMapper();
		fieldSetMapper.setTargetType(BankTransaction.class);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public ItemProcessor<BankTransaction,BankTransaction> itemProcessor()
	{
		return new ItemProcessor<BankTransaction, BankTransaction>() {

			@Override
			public BankTransaction process(BankTransaction item) throws Exception {
				return null;
			}

		};
	}

}
