package de.servicezombie.csv2qif.vo;

public class CsvConfigProvider {

	private int skipLines = 0;
	private char fieldSeparator = '\t';
	private Character quoteChar = null;

	public int getSkipLines() {
		return skipLines;
	}

	public void setSkipLines(int skipLines) {
		this.skipLines = skipLines;
	}

	public char getFieldSeparator() {
		return fieldSeparator;
	}

	public void setFieldSeparator(char fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	public Character getQuoteChar() {
		return quoteChar;
	}

	public void setQuoteChar(Character quoteChar) {
		this.quoteChar = quoteChar;
	}
	
	public boolean isQuoteCharUsed() {
		return quoteChar != null;
	}

}
