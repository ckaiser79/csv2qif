package de.servicezombie.testdata;

import java.util.ArrayList;
import java.util.List;


public class CsvDataProvider {
	
	public static List<String[]> querySomeTransactions(int number) {
		List<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < number; i++) {
			data.add(new String[] { String.valueOf(i), "#" + i });	
		} 
		return data;
	}
	
}
