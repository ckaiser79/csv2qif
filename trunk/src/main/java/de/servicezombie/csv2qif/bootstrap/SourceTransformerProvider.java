package de.servicezombie.csv2qif.bootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.internal.Nullable;
import com.google.inject.name.Named;

import de.servicezombie.csv2qif.service.IConfigurationReaderService;
import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class SourceTransformerProvider implements Provider<Transformer<QuickenTransaction>> {

	private static final Log log = LogFactory.getLog(SourceTransformerProvider.class);
	
	private IConfigurationReaderService configuration;
	private String transformerName;

	@Inject 
	public void setTransformerName(@Nullable @Named("transformer-id") String transformerName) {
		this.transformerName = transformerName;
	}
	
	@Inject
	public void setConfigurationReaderService(IConfigurationReaderService configuration) {
		this.configuration = configuration;
	}
	
	public Transformer<QuickenTransaction> get() {
		
		Transformer<QuickenTransaction> sourceTransformer = configuration.getSourceTransformerFor(transformerName);
		if (log.isDebugEnabled()) {
			log.debug("get: rules = " + sourceTransformer);
		}		
		return sourceTransformer;
	}
}
