package de.servicezombie.core.args4j.handler;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class ClassForNameOptionHandler extends OptionHandler<Class<?>> {

	public ClassForNameOptionHandler(CmdLineParser parser, OptionDef option, Setter<Class<?>> setter) {
		super(parser, option, setter);
	}

	@Override
	public String getDefaultMetaVariable() {
		return "FQ CLASS NAME";
	}

	@Override
	public int parseArguments(Parameters param) throws CmdLineException {
		String clazzName = param.getParameter(0);
		try {
			Class<?> clazz = Class.forName(clazzName);
			setter.addValue(clazz);
		}
		catch (ClassNotFoundException e) {
			throw new CmdLineException("unable to set class of name '" + clazzName + "'. Is it on classpath?", e);
		}

		return 1;
	}

}
