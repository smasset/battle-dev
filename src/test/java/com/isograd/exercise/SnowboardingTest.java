package com.isograd.exercise;

import java.io.InputStream;

import org.junit.Test;

public class SnowboardingTest extends AbstractTest {
	private static final String RESOURCE_DIR = "/snowboarding";

	public SnowboardingTest() {
		super(RESOURCE_DIR);
	}

	public String solve(InputStream input) {
		return new IsoContest().solveSnowboarding(input);
	}

	@Test
	public void testInput1() {
		checkOutput("input1.txt", "output1.txt");
	}

	@Test
	public void testInput2() {
		checkOutput("input2.txt", "output2.txt");
	}

	@Test
	public void testInput3() {
		checkOutput("input3.txt", "output3.txt");
	}

	@Test
	public void testInput4() {
		checkOutput("input4.txt", "output4.txt");
	}

	@Test
	public void testInput5() {
		checkOutput("input5.txt", "output5.txt");
	}

	@Test
	public void testInput6() {
		checkOutput("input6.txt", "output6.txt");
	}

	@Test
	public void testInput7() {
		checkOutput("input7.txt", "output7.txt");
	}

	@Test
	public void testInput8() {
		checkOutput("input8.txt", "output8.txt");
	}

	@Test
	public void testInput9() {
		checkOutput("input9.txt", "output9.txt");
	}

}
