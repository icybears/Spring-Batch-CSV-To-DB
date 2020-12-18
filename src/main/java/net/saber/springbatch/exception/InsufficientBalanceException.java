package net.saber.springbatch.exception;

public class InsufficientBalanceException extends RuntimeException{

	private static final long serialVersionUID = -7254729028137068642L;
	
	public InsufficientBalanceException(String message) {
		super(message);
	}

}
