package net.saber.springbatch.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import net.saber.springbatch.dto.TransactionDTO;
import net.saber.springbatch.entity.Compte;
import net.saber.springbatch.exception.AccountNotFoundException;
import net.saber.springbatch.exception.ErrorMessages;
import net.saber.springbatch.exception.InsufficientBalanceException;
import net.saber.springbatch.repository.CompteRepository;

@Component
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private CompteRepository compteRepository;

	@Value("${app.transactions.csv.path}")
	private String csvFilePath;

	public void validateTransaction(TransactionDTO transaction) {

		Optional<Compte> result = compteRepository.findById(transaction.getIdCompte());

		if (result.isEmpty()) {
			throw new AccountNotFoundException(ErrorMessages.ACCOUNT_NOT_FOUND.getMessage());
		}

		Compte compte = result.get();
		
		double solde = compte.getSolde();
		double montant = transaction.getMontant();
		
		if (montant > solde) {
			throw new InsufficientBalanceException(ErrorMessages.INSUFFICIENT_BALANCE.getMessage());
		}
		
		compte.setSolde(solde - montant);
		
		compteRepository.save(compte);

	}

	@Override
	public void saveTransactionToCSV(TransactionDTO transaction) throws IOException {

		File csvFile = (new ClassPathResource(csvFilePath)).getFile();

		System.out.println("file exists ? " + csvFile.exists());
		System.out.println(csvFile.getAbsolutePath());

		try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile, true))) {
			String data = Arrays.stream(parse(transaction)).map(this::escapeSpecialCharacters)
					.collect(Collectors.joining(","));
			System.out.println(data);
			writer.println(data);

		}

	}

	@Override
	public String[] parse(TransactionDTO transaction) {

		return new String[] { transaction.getIdTransaction().toString(), transaction.getIdCompte().toString(),
				transaction.getMontant().toString(),
				transaction.getDateTransaction().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) };
	}

	@Override
	public String escapeSpecialCharacters(String data) {

		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}
}
