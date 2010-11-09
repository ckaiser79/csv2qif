package de.servicezombie.csv2qif.util;

import org.apache.commons.lang.ArrayUtils;

import de.servicezombie.csv2qif.def.ErrorCodes;

public class ErrorCodeAwareException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ErrorCodes errorCode;
	private Object[] args;

	public ErrorCodeAwareException() {
		this(null, ErrorCodes.UNKNOWN.toString());
	}

	public ErrorCodeAwareException(Throwable cause, ErrorCodes code, Object... args) {
		super(ArrayUtils.toString(args), cause);
		this.errorCode = code;
		this.args = args;
	}

	public ErrorCodeAwareException(ErrorCodes code, Object... args) {
		this(null, code, args);
	}

	public ErrorCodes getErrorCode() {
		return errorCode;
	}

	public Object[] getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.errorCode + ": " + super.getMessage();
	}	
	
	
	
	
}
