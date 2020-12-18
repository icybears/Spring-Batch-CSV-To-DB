package net.saber.springbatch.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.ISBN;
import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TransactionDTO implements Serializable{

	private static final long serialVersionUID = -2339894390257463655L;
	
	@NotNull
	@Positive
	private Long idTransaction;
	
	@NotNull
	@Positive
	private Long idCompte;

	@NotNull
	@Positive
	private Double montant;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dateTransaction;
}
