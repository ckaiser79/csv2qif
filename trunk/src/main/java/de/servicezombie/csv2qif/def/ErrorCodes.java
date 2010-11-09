package de.servicezombie.csv2qif.def;

public enum ErrorCodes {

	UNKNOWN(-1), 
	
	INVALID_NUMBER,
	INVALID_DATE, 
	FILE_DOES_NOT_EXIST, 
	UNABLE_TO_READ_FILE, 
	UNABLE_TO_WRITE_FILE, 
	INVALID_XPATH, 
	INVALID_XML_CONFIGURATION, 
	INPUTFILE_NOT_READABLE, 
	
	TRANSFORMERXML_NOT_READABLE, 
	
	BSH_EXECUTION_ERROR, 
	BSH_SCRIPT_NOT_FOUND, 
	BSH_SCRIPT_NOT_SET, 
	BSH_SCRIPT_NOT_READABLE, 
	
	CONFIG_MISSING_TRANSFORMER_CLASS, 
	CONFIG_INVALID_PROPERTY,
	CONFIG_INVALID_FILE_STRUCTURE, 
	CONFIG_INVALID_TRANSFORMER_NAME;

	private int number;

	private ErrorCodes() {
		this.number = -99;
	}

	private ErrorCodes(int rc) {
		this.number = rc;
	}

	public int getNumber() {
		return number;
	}

}
