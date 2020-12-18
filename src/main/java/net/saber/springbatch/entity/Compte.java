package net.saber.springbatch.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter@Setter@ToString
public class Compte implements Serializable{

	private static final long serialVersionUID = 5321796531051399931L;

	@Id
	private Long idCompte;
	
	private Double solde;
	
	@OneToMany(mappedBy = "proprietaire")
	private List<Transaction> transactions = new ArrayList<>();
	
	public Compte() {
		super();
	}
	
	public Compte(long idCompte, double solde) {
		super();
		this.idCompte = idCompte;
		this.solde = solde;
	}


	public void addTransaction(Transaction transaction) {
		transaction.setProprietaire(this);
	}
	
	public void removeTransaction(Transaction transaction) {
		transaction.setProprietaire(null);
	}

	
}
