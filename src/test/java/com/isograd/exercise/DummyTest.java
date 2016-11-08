package com.isograd.exercise;

import java.io.InputStream;

import org.junit.Test;

public class DummyTest extends AbstractTest {
	private static final String RESOURCE_DIR = "/dummy";

	public DummyTest() {
		super(RESOURCE_DIR);
	}

	public String solve(InputStream input) {
		return new IsoContest().dummySolve(input);
	}

	@Test
	public void testInput() {
		checkOutput("input.txt", "output.txt");
	}
}
