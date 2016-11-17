/*******
 * Read input from System.in
 * Use System.out.println to ouput your result.
 * Use:
 *  IsoContestBase.localEcho( variable)
 * to display variable in a dedicated area.
 * ***/
package com.isograd.exercise;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IsoContest {

	private class Accumulator<T> extends HashMap<T, Integer> {
		private static final long serialVersionUID = 5480917823114056491L;

		public Accumulator(int initialSize) {
			super(initialSize);
		}

		public void addCount(T key, Integer count) {
			this.put(key, this.getCount(key) + count);
		}

		public void addCount(T key) {
			this.addCount(key, 1);
		}

		public Integer getCount(T key) {
			Integer currentCount = this.get(key);

			if (currentCount == null) {
				currentCount = 0;
			}

			return currentCount;
		}
	}

	public String solveGants(InputStream input) {
		Integer count = null;
		String line = null;

		Accumulator<String> gloves = null;
		try (Scanner sc = new Scanner(input)) {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				/* Lisez les données et effectuez votre traitement */
				if (count == null) {
					count = Integer.parseInt(line);
					gloves = new Accumulator<>(count);
				} else {
					gloves.addCount(line);
				}
			}
		}

		/*
		 * Vous pouvez aussi effectuer votre traitement une fois que vous avez
		 * lu toutes les données.
		 */
		return gloves.values().stream().map(c -> Math.floor(c / 2d)).collect(Collectors.summingInt(Double::intValue))
				.toString();
	}

	public String dummySolve(InputStream input) {
		String line = null;

		try (Scanner sc = new Scanner(input)) {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				/* Lisez les données et effectuez votre traitement */
			}
		}

		/*
		 * Vous pouvez aussi effectuer votre traitement une fois que vous avez
		 * lu toutes les données.
		 */
		return line;
	}

	public static void main(String[] argv) throws Exception {
		IsoContest contest = new IsoContest();
		final InputStream input;
		if (argv.length != 0) {
			input = new FileInputStream(argv[0]);
		} else {
			input = System.in;
		}

		System.out.println(contest.solveGants(input));
	}
}
