package net.saber.springbatch.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import net.saber.springbatch.dto.TransactionDTO;

@Service
public interface TransactionService {

	void saveTransactionToCSV(TransactionDTO transaction) throws IOException;
	
	void validateTransaction(TransactionDTO transaction);

	String escapeSpecialCharacters(String data);

	String[] parse(TransactionDTO transaction);

}