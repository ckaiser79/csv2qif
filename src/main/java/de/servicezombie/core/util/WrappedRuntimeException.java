package de.servicezombie.core.util;

/**
 * for exception that should normally be caught and can happen 
 * but in case we are too lazy to do so.
 * 
 * @author Christian Kaiser <ckaiser@gmx.org>
 *
 */
public class WrappedRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public WrappedRuntimeException(Throwable cause) {
		super(cause);
	}

}
