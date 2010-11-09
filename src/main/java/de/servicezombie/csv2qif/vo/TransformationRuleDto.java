package de.servicezombie.csv2qif.vo;

import java.util.regex.Pattern;

public class TransformationRuleDto {

	private String result = null;
	private String patternField = null;
	private String resultField = null;
	private String transformerType = null;

	private Pattern pattern = null;

	public String toString() {
		return transformerType + ": if /" + pattern + "/ @ " + patternField + " => " + result + " @ " + resultField;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPatternField() {
		return patternField;
	}

	public void setPatternField(String patternField) {
		this.patternField = patternField;
	}

	public String getResultField() {
		return resultField;
	}

	public void setResultField(String resultField) {
		this.resultField = resultField;
	}

	public String getTransformerType() {
		return transformerType;
	}

	public void setTransformerType(String transformerType) {
		this.transformerType = transformerType;
	}

	public Pattern getPattern() {
		return pattern;
	}

	public void setPatternAsString(String pattern) {
		this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}

}
