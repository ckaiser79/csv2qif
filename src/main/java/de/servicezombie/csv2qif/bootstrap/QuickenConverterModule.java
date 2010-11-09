package de.servicezombie.csv2qif.bootstrap;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.google.inject.util.Providers;

import de.servicezombie.core.util.StringUtils;
import de.servicezombie.csv2qif.service.IConfigurationReaderService;
import de.servicezombie.csv2qif.service.IConverterService;
import de.servicezombie.csv2qif.service.ICsvReaderService;
import de.servicezombie.csv2qif.service.IQuickenWriterService;
import de.servicezombie.csv2qif.service.impl.CsvReaderService;
import de.servicezombie.csv2qif.service.impl.CsvSourceConverterService;
import de.servicezombie.csv2qif.service.impl.QuickenWriterService;
import de.servicezombie.csv2qif.service.impl.XmlConfigurationReaderService;
import de.servicezombie.csv2qif.util.QuickenRulebasedTransformer;
import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.CommandlineOptions;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class QuickenConverterModule extends AbstractModule {

	private TypeLiteral<Transformer<QuickenTransaction>> transformerWithQuickenTransaction = 
		new TypeLiteral<Transformer<QuickenTransaction>>() {};
		
	private CommandlineOptions commandline;
	
	@Override
	protected void configure() {
		
		bindConstant().annotatedWith(Names.named("xml-document")).to(commandline.getTransformerXml().getAbsolutePath());
		
		bind(String.class).annotatedWith(Names.named("transformer-id")).toProvider(Providers.of(commandline.getTransformerId()));
					
		bind(IConfigurationReaderService.class).to(XmlConfigurationReaderService.class).in(Singleton.class);
		
		bind(ICsvReaderService.class).to(CsvReaderService.class).in(Singleton.class);
		bind(IQuickenWriterService.class).to(QuickenWriterService.class).in(Singleton.class);		
				
		bind(new TypeLiteral<IConverterService<String[]>>() {}).to(CsvSourceConverterService.class).in(Singleton.class);
		
		bind(QuickenRulebasedTransformer.class).toProvider(PatternMatchingRulesProvider.class);
		
		bind(transformerWithQuickenTransaction).annotatedWith(Names.named("post-process-transformer"))
			.to(QuickenRulebasedTransformer.class);
		
		bind(transformerWithQuickenTransaction).annotatedWith(Names.named("source-transformer"))
			.toProvider(SourceTransformerProvider.class);
	}
	
//	private void bindNamedOptionalDependency(String name, Class<? extends Transformer<QuickenTransaction>> configuredClazz,
//			Class<? extends Transformer<QuickenTransaction>> defaultClazz) {
//		
//		if (configuredClazz != null) {		
//			bind(transformerWithQuickenTransaction).annotatedWith(Names.named(name)).to(configuredClazz);
//		}
//		else if (defaultClazz != null) {
//			bind(transformerWithQuickenTransaction).annotatedWith(Names.named(name)).to(defaultClazz);
//		}
//		
//	}
		
	public void setCommandline(CommandlineOptions commandline) {
		this.commandline = commandline;
	}

}
