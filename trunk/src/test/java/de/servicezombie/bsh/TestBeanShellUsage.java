package de.servicezombie.bsh;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;

import bsh.Interpreter;

public class TestBeanShellUsage {

	@Test
	public void testBehaviour() throws Exception {

		Interpreter i = new Interpreter();  // Construct an interpreter
		i.set("foo", 5);                    // Set variables
		i.set("date", new Date() ); 

		Date date = (Date)i.get("date");    // retrieve a variable

		// Eval a statement and get the result
		i.eval("bar = foo*10");             
		System.out.println( i.get("bar") );

		// Source an external script file
		Object result = i.source("src/test/resources/sample.bsh");
		Assert.assertEquals(result, 50);
		System.out.println("sourced " + result);

	}
}
