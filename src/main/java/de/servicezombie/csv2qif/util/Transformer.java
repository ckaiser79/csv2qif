package de.servicezombie.csv2qif.util;

/**
 * original is from commons collections. but we do not need 
 * whole library
 * 
 * @author CK
 */
public interface Transformer<T> {	
	T transform(Object from);
}
