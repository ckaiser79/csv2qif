package de.servicezombie.csv2qif.vo;

import java.io.File;

import org.kohsuke.args4j.Option;

import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.util.ErrorCodeAwareException;
import de.servicezombie.csv2qif.util.Transformer;

/**
 * csv2qif -t transform.xml -d foobar.csv -o foobar.qif
 */
public class CommandlineOptions {

	@Option(name="-i",usage="read data from this file", required = true)
	private File input;
	
	@Option(name="-o",usage="output to this file", required = false)
	private File output;
	
	@Option(name="-c",usage="config file to use", required = true)
	private File transformerXml;
	
	@Option(name="-n",usage="name of transformer element in configuration, which should be used", required = false)
	private String transformerId;

	public File getInput() {
		return input;
	}

	public void setInput(File input) {
		this.input = input;
	}

	public File getOutput() {
		return output;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	public File getTransformerXml() {
		return transformerXml;
	}

	public void setTransformerXml(File transformerXml) {
		this.transformerXml = transformerXml;
	}
	
	public void verifyOptions() throws ErrorCodeAwareException {
		if (!input.canRead()) {
			throw new ErrorCodeAwareException(ErrorCodes.INPUTFILE_NOT_READABLE);
		}
		
		if (transformerXml != null && !(transformerXml.canRead() && transformerXml.isFile())) {
			throw new ErrorCodeAwareException(ErrorCodes.TRANSFORMERXML_NOT_READABLE);
		}
	}
	
	public String getTransformerId() {
		return transformerId;
	}
	

}
