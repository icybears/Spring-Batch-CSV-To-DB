package net.saber.springbatch.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.saber.springbatch.dto.TransactionDTO;
import net.saber.springbatch.service.TransactionService;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionRestController {
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping
	public ResponseEntity<HttpStatus> createTransaction(@RequestBody @Valid TransactionDTO transaction) throws IOException {
		
		transactionService.validateTransaction(transaction);
		transactionService.saveTransactionToCSV(transaction);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
