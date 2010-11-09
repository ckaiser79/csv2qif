package de.servicezombie.core.util;


public class StringUtils {

	/* usefull in google guice */
	public static final String NULL = null;

	public static final char evaluateEsacpedChar(String input) {
		if(input.isEmpty()) {
			return '\0';
		}
		
		if (!input.startsWith("\\") || input.length() < 2) {
			return input.charAt(0);
		}
		
		char second = input.charAt(1);
		switch(second) {
		case 't':
			return '\t';
		case 'n':
			return '\n';
			default:
				throw new IllegalArgumentException("Character " + Character.toString(second) + " is unknown for escaping");
		}
	}
	
}
