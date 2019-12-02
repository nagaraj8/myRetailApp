package com.myretail.api.exception;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mongodb.MongoException;

/**
 * Target Case Study
 * @author Nagaraj Hegde
 *
 */
@Component
@ControllerAdvice
public class MyRetailExceptionHandler extends ResponseEntityExceptionHandler{
	
	private final Logger log = Logger.getLogger(MyRetailExceptionHandler.class.getName());
	
	
	@ExceptionHandler(IOException.class)
	public final ResponseEntity<CustomErrorMessage> ioExceptionHandler(IOException ex){
		CustomErrorMessage exceptionResponse= new CustomErrorMessage(ex.getMessage()," JSON parsing failed while retreiving product name");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<CustomErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(HttpClientErrorException.class)
	public final ResponseEntity<CustomErrorMessage> httpClientErrorExceptionHandler(HttpClientErrorException ex){
		CustomErrorMessage exceptionResponse= new CustomErrorMessage(ex.getMessage(),"Product details are not available for requested id");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<CustomErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<CustomErrorMessage> methodArgTypeMiExceptionHandler(MethodArgumentTypeMismatchException ex){
		CustomErrorMessage exceptionResponse= new CustomErrorMessage(ex.getMessage()," Failed to convert value of argument, expected number but got something else  ");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<CustomErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MongoException.class)
	public final ResponseEntity<CustomErrorMessage> mongoExceptionHandler(MongoException ex){
		CustomErrorMessage exceptionResponse= new CustomErrorMessage(ex.getMessage()," Product details are not available in mongo dbcollection 'productprice' ");
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<CustomErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CustomErrorMessage> exceptionHandler(Exception ex){
		CustomErrorMessage exceptionResponse= new CustomErrorMessage(ex.getMessage(),ex.getMessage());
		log.debug("customErrorMessage : "+exceptionResponse);
		return new ResponseEntity<CustomErrorMessage>(exceptionResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
}
