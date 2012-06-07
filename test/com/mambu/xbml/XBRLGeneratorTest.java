package com.mambu.xbml;


import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mambu.xbrl.server.XBRLGenerator;
import com.mambu.xbrl.shared.Duration;

public class XBRLGeneratorTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLinkGeneration() {
		XBRLGenerator xBRLGenerator = new XBRLGenerator();
		xBRLGenerator.addLink();
		xBRLGenerator.addContext( new ArrayList<Duration>());
		String generatedXML = xBRLGenerator.generate();
		System.out.println(generatedXML);
		assertFalse(generatedXML.isEmpty());
	}

}
