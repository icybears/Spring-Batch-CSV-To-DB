package net.saber.springbatch.exception;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class AppExceptionHandler {


	@ExceptionHandler(value= {InsufficientBalanceException.class})
	public ResponseEntity<Object> handleInsufficientBalance(InsufficientBalanceException e,WebRequest req){
		return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}	
	
	@ExceptionHandler(value= {AccountNotFoundException.class})
	public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException e,WebRequest req){
		return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}	

	@ExceptionHandler(value= {IOException.class})
	public ResponseEntity<Object> handleIOException(IOException e,WebRequest req){
		return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}	

	//handle every other exception
	@ExceptionHandler(value={Exception.class})
	public ResponseEntity<Object> handleOtherExceptions(Exception e, WebRequest req){
	return new ResponseEntity<>(e.getMessage(),new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
}
}