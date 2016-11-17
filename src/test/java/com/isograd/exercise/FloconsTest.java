package com.isograd.exercise;

import java.io.InputStream;

import org.junit.Test;

public class FloconsTest extends AbstractTest {
	private static final String RESOURCE_DIR = "/flocons";

	public FloconsTest() {
		super(RESOURCE_DIR);
	}

	public String solve(InputStream input) {
		return new IsoContest().solveFlocons(input);
	}

	@Test
	public void testInput1() {
		checkOutput("input1.txt", "output1.txt");
	}

	@Test
	public void testInput2() {
		checkOutput("input2.txt", "output2.txt");
	}
}
