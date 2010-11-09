package de.servicezombie.csv2qif.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import bsh.EvalError;
import bsh.Interpreter;
import de.servicezombie.core.util.WrappedRuntimeException;
import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class BeanshellScriptedQuickenTransformer implements Transformer<QuickenTransaction> {
	
	private static final Log log = LogFactory.getLog(BeanshellScriptedQuickenTransformer.class);
	
	private static final String VAR_TRANSACTION = "transaction";
	private static final String VAR_SOURCE_ARRAY = "source";
	
	private String sourceFileScript;
	
	public void setSourceFileAsString(String sourceFileScript) {
		this.sourceFileScript = new File(sourceFileScript).getAbsolutePath();
	}

	public QuickenTransaction transform(Object from) {
		QuickenTransaction qt = new QuickenTransaction();		
		String[] source = (String[]) from;

		if (log.isDebugEnabled()) {
			log.debug("transform: scriptFile = " + sourceFileScript);
			log.debug("transform: data = " + Arrays.toString(source));
		}
		
		if (sourceFileScript == null) {
			throw new ErrorCodeAwareException(ErrorCodes.BSH_SCRIPT_NOT_SET);
		}
		
		Interpreter bshInterpreter = new Interpreter();
		
		try {
			
			bshInterpreter.set(VAR_SOURCE_ARRAY, source);
			bshInterpreter.set(VAR_TRANSACTION, qt);
			Object result = bshInterpreter.source(sourceFileScript);
		
			if (result instanceof Throwable) {
				throw new WrappedRuntimeException((Throwable)result);
			}
			
		}
		catch(EvalError e) {
			
			if (log.isInfoEnabled()) {
				log.info("beanshell stacktrace dump is", e);
			}
			
			throw new ErrorCodeAwareException(e, ErrorCodes.BSH_EXECUTION_ERROR, e.getMessage());			
		}
		catch(FileNotFoundException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.BSH_SCRIPT_NOT_FOUND, sourceFileScript);
		}
		catch(IOException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.BSH_SCRIPT_NOT_READABLE, sourceFileScript, e.getMessage());
		}
	
		if(log.isDebugEnabled()) {
			log.debug("<< qt = " + qt);
		}
		return qt;		
	}

}
