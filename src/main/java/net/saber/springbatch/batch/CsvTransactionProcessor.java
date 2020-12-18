package net.saber.springbatch.batch;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import net.saber.springbatch.dto.TransactionDTO;
import net.saber.springbatch.entity.Compte;
import net.saber.springbatch.entity.Transaction;
import net.saber.springbatch.repository.CompteRepository;

public class CsvTransactionProcessor implements ItemProcessor<TransactionDTO, Transaction>{

	@Autowired
	private CompteRepository compteRepository; 
	
	@Override
	public Transaction process(TransactionDTO item) throws Exception {

		Optional<Compte> compte = compteRepository.findById(item.getIdCompte());
		
		Transaction transaction = null;
		
		if(compte.isPresent()) {
			transaction = new Transaction(item.getIdTransaction(), item.getMontant(), item.getDateTransaction(), LocalDateTime.now());
			transaction.setProprietaire(compte.get());
			System.out.println("inside process method: "+transaction.toString());
		}
		

		return transaction;
	}

}
