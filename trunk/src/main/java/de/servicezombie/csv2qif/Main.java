package de.servicezombie.csv2qif;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import de.servicezombie.core.args4j.handler.ClassForNameOptionHandler;
import de.servicezombie.core.util.IOUtils;
import de.servicezombie.csv2qif.bootstrap.QuickenConverterModule;
import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.service.IConfigurationReaderService;
import de.servicezombie.csv2qif.service.IConverterService;
import de.servicezombie.csv2qif.service.ICsvReaderService;
import de.servicezombie.csv2qif.service.IQuickenWriterService;
import de.servicezombie.csv2qif.service.impl.XmlConfigurationReaderService;
import de.servicezombie.csv2qif.util.ErrorCodeAwareException;
import de.servicezombie.csv2qif.vo.CommandlineOptions;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class Main {

	private static final Log log = LogFactory.getLog(Main.class);

	private CommandlineOptions cmd;
	private Injector injector;

	private IConfigurationReaderService xmlConfiguration;
	
	public Main(String[] args) {
		
		parseCommandLineOrUsage(args);			
		QuickenConverterModule module = new QuickenConverterModule();
		
		module.setCommandline(cmd);
		
		injector = Guice.createInjector(module);
		xmlConfiguration = injector.getInstance(XmlConfigurationReaderService.class);
		
	}

	private void parseCommandLineOrUsage(String[] args) {
		cmd = new CommandlineOptions();
		
		CmdLineParser.registerHandler(Class.class, ClassForNameOptionHandler.class);
	    CmdLineParser parser = new CmdLineParser(cmd);
	    
		try {
		    parser.parseArgument(args);
		    cmd.verifyOptions();
		} catch( CmdLineException e ) {
		    System.err.println(e.getMessage());
		    System.err.println("java -cp ... de.servicezombie.csv2qif.Main [-t transform.xml] <-d foobar.csv> [-o foobar.qif]");
		    parser.printUsage(System.err);
		    System.exit(2);
		}
		
	}

	public void run() throws ErrorCodeAwareException {
	
		/* CK read csv (7 Nov 2010) */
		Iterator<String[]> csvIterator = readCsvData();
				
		xmlConfiguration.getSourceTransformerFor(cmd.getTransformerId());
		IConverterService<String[]> converterService = injector.getInstance(Key.get(new TypeLiteral<IConverterService<String[]>>() {}));
				
		Iterator<QuickenTransaction> quickenIterator = converterService.convertToQuicken(csvIterator);
		
		/* CK write quicken file (7 Nov 2010) */
		writeQuickenData(quickenIterator);
		
	}

	private void writeQuickenData(Iterator<QuickenTransaction> quickenIterator) {
		IQuickenWriterService quickenWriterService = injector.getInstance(IQuickenWriterService.class);
		
		Writer target = null;
		
		try {
			
			if (cmd.getOutput() == null) {
				target = new OutputStreamWriter(System.out);
			}
			else {
				target = new FileWriter(cmd.getOutput());
			}
			quickenWriterService.writeQuickenFile(target, quickenIterator);
			target.flush();
		}
		catch (IOException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.UNABLE_TO_WRITE_FILE, e.getMessage());
		}
		finally {
			IOUtils.closeSilent(target);
		}
	}
	
	private Iterator<String[]> readCsvData() {
		
		Iterator<String[]> csvIterator = null;
		Reader inputReader = null;
		ICsvReaderService csvReaderService = injector.getInstance(ICsvReaderService.class);
		
		CsvConfigProvider config = xmlConfiguration.getCsvConfigurationFor(cmd.getTransformerId());
		
		try {
			inputReader = new FileReader(cmd.getInput());		
			csvIterator = csvReaderService.loadCsv(inputReader, config);
		}
		catch (FileNotFoundException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.FILE_DOES_NOT_EXIST, cmd.getInput().getAbsolutePath());
		}
		catch (IOException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.UNABLE_TO_READ_FILE, cmd.getInput().getAbsolutePath());
		}
		finally {
			IOUtils.closeSilent(inputReader);
		}
		
		return csvIterator;
	}

	public static void main(String[] args) {
		Main m = new Main(args);
		try {
			m.run();
		}
		catch (ErrorCodeAwareException e) {
			System.err.println("Unexpected exception: " + e.getLocalizedMessage());
			
			/* CK sometome does not work (10 Nov 2010) */
			if (log.isDebugEnabled()) {
				log.debug("stacktrace dump", e);
				//e.printStackTrace();
			}		
			log.fatal("Unexpected exception: " + e.getMessage());
		}
	}
	
	
	
	
}
