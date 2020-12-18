package net.saber.springbatch.exception;

public enum ErrorMessages {
	
	INSUFFICIENT_BALANCE("Solde insuffisant pour effectuer cette transaction."),
	ACCOUNT_NOT_FOUND("Le compte associé à cette transaction n'existe pas."),
	INTERNAL_ERROR("Impossible d'effectuer cette operation pour le moment.");
	
	
	private String errorMessage;
	
	ErrorMessages(String msg) {
		errorMessage = msg;
	}

	public String getMessage() {
		return this.errorMessage;
	}

}
