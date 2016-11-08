package com.isograd.exercise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public abstract class AbstractTest {

	private String resourceDirName = null;

	public AbstractTest(String resourceDirName) {
		this.resourceDirName = resourceDirName;
	}

	private InputStream getResource(String filename) {
		return this.getClass().getResourceAsStream(String.format("%s/%s", resourceDirName, filename));
	}

	private String extractExpectedOutput(String outputFilename) {
		StringBuilder output = new StringBuilder();

		try (Scanner scanner = new Scanner(this.getResource(outputFilename))) {
			while (scanner.hasNext()) {
				output.append(scanner.nextLine());
			}
		}

		return output.toString();
	}

	protected void checkOutput(String inputFile, String expectedOutputFile) {
		try (InputStream inputStream = this.getResource(inputFile)) {
			assertEquals(extractExpectedOutput(expectedOutputFile), this.solve(inputStream));
		} catch (IOException e) {
			fail(String.format("Unexpected IO error", e.getMessage()));
		}
	}

	public abstract String solve(InputStream input);
}
