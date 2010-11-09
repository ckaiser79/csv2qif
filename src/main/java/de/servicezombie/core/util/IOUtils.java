package de.servicezombie.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IOUtils {
	
	private static final Log log = LogFactory.getLog(IOUtils.class);
	
	public static void closeSilent(Reader r) {
		if (r == null) {
			return;
		} 
		
		try {
			r.close();
		}
		catch(IOException e) {
			log.error("Unable to close reader " + r + ". Message: " + e.getMessage());
			if (log.isDebugEnabled()) {
				log.debug("closeSilent: stacktrace", e);
			}
		}
	}
	
	public static void closeSilent(Writer r) {
		if (r == null) {
			return;
		} 
		
		try {
			r.close();
		}
		catch(IOException e) {
			log.error("Unable to close reader " + r + ". Message: " + e.getMessage());
			if (log.isDebugEnabled()) {
				log.debug("closeSilent: stacktrace", e);
			}
		}
	}
	
	public static void closeSilent(InputStream r) {
		if (r == null) {
			return;
		} 
		
		try {			
			r.close();
		}
		catch(IOException e) {
			log.error("Unable to close reader " + r + ". Message: " + e.getMessage());
			if (log.isDebugEnabled()) {
				log.debug("closeSilent: stacktrace", e);
			}
		}
	}
	
	public static void closeSilent(OutputStream r) {
		if (r == null) {
			return;
		} 
		
		try {
			r.close();
		}
		catch(IOException e) {
			log.error("Unable to close reader " + r + ". Message: " + e.getMessage());
			if (log.isDebugEnabled()) {
				log.debug("closeSilent: stacktrace", e);
			}
		}
	}

}
