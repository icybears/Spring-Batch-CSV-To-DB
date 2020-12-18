package net.saber.springbatch.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter@Setter@ToString
public class Transaction implements Serializable{

	private static final long serialVersionUID = 7626840123454477521L;

	@Id
	private Long idTransaction;
	
	private Double montant;
	
	private LocalDateTime dateTransaction;
	
	private LocalDateTime dateDebit;
	
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name="id_compte")
	private Compte proprietaire;
	

	public Transaction() {
		super();
	}
	
	public Transaction(long idTransaction, double montant, LocalDateTime dateTransaction, LocalDateTime dateDebit) {
		super();
		this.idTransaction = idTransaction;
		this.montant = montant;
		this.dateTransaction = dateTransaction;
		this.dateDebit = dateDebit;
	}

	public void setProprietaire(Compte proprietaire) {
		
		if(this.proprietaire != null) {
			this.proprietaire.getTransactions().remove(this);
		}
		
		this.proprietaire = proprietaire;
		
		if(proprietaire != null) {
			this.proprietaire.getTransactions().add(this);
		}
	}


	
}
