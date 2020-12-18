package net.saber.springbatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import net.saber.springbatch.entity.Transaction;
import net.saber.springbatch.repository.TransactionRepository;

public class CsvTransactionWriter implements ItemWriter<Transaction>{

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Override
	public void write(List<? extends Transaction> items) throws Exception {
		for(Transaction t: items) {
			transactionRepository.save(t);
		}
		
	}

}
