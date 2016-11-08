/*******
 * Read input from System.in
 * Use System.out.println to ouput your result.
 * Use:
 *  IsoContestBase.localEcho( variable)
 * to display variable in a dedicated area.
 * ***/
package com.isograd.exercise;

import java.util.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class IsoContest {

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

		System.out.println(contest.dummySolve(input));
	}
}
