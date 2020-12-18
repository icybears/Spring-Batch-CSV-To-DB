package net.saber.springbatch.exception;

public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5743926307992543112L;

	public AccountNotFoundException(String message) {
		super(message);
	}
	
}
