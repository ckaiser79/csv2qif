package de.servicezombie.csv2qif.service;

import java.util.List;

import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;
import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public interface IConfigurationReaderService {
	
	String getDefaultConverterName();
	Transformer<QuickenTransaction> getSourceTransformerFor(String name);
	
	List<TransformationRuleDto> getTransformationRulesFor(String type);
	List<TransformationRuleDto> getAllTransformationRules();

	CsvConfigProvider getCsvConfigurationFor(String name);
}
