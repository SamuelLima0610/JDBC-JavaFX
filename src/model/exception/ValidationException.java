package model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errors = new HashMap<>();
	
	public ValidationException(String message) {
		super(message);
	}
	
	public Map<String, String> getErrors(){
		return this.errors;
	}
	
	public void addError(String textField, String error) {
		errors.put(textField, error);
	}
}
