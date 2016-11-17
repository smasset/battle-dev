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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IsoContest {

	private static final String OK = "OK";

	private static final String KO = "KO";

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

	public String solveFlocons(InputStream input) {
		Integer size = null;
		String line = null;

		try (Scanner sc = new Scanner(input)) {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				/* Lisez les données et effectuez votre traitement */
				if (size == null) {
					size = Integer.parseInt(line);
				}
			}
		}

		/*
		 * Vous pouvez aussi effectuer votre traitement une fois que vous avez
		 * lu toutes les données.
		 */
		Integer halfSize = Double.valueOf(Math.floor(size / 2d)).intValue();
		Integer startColumn = halfSize;
		Integer width = 0;

		StringBuilder output = new StringBuilder();
		for (int row = 0; row < size; ++row) {
			if (row > 0) {
				output.append('\n');
			}

			for (int column = 0; column < size; ++column) {
				if (column < startColumn || column > startColumn + width) {
					output.append('.');
				} else {
					output.append('*');
				}
			}

			if (row < halfSize) {
				startColumn -= 1;
				width += 2;
			} else {
				startColumn += 1;
				width -= 2;
			}
		}

		return output.toString();
	}

	private class Position {
		private Integer row;
		private Integer column;

		public Position(int row, int column) {
			this.row = row;
			this.column = column;
		}

		@Override
		public boolean equals(Object obj) {
			Position other = (Position) obj;
			return this.row.equals(other.row) && this.column.equals(other.column);
		}

		@Override
		public String toString() {
			return String.format("[%d %d]", row, column);
		}

		public Double getDistanceTo(Position other) {
			Double distance = 0d;

			if (!this.equals(other)) {
				Integer xDistance = this.row - other.row;
				Integer yDistance = this.column - other.column;

				distance = Math.sqrt(xDistance.doubleValue() * xDistance.doubleValue()
						+ yDistance.doubleValue() * yDistance.doubleValue());
			}

			return distance;
		}
	}

	private class Circle {
		private Position center = null;
		private Integer radius = null;

		public Circle(String... params) {
			this.center = new Position(Integer.valueOf(params[0]), Integer.valueOf(params[1]));
			this.radius = Integer.valueOf(params[2]);
		}

		public boolean intersects(Circle other) {
			Double distance = this.center.getDistanceTo(other.center);
			Integer maxRadius = Math.max(this.radius, other.radius);
			Integer minRadius = Math.min(this.radius, other.radius);
			return distance >= maxRadius - minRadius && distance <= maxRadius + minRadius;
		}

		@Override
		public String toString() {
			return String.format("%d %d %d", center.row, center.column, radius);
		}
	}

	public String solveTopographie(InputStream input) {
		Integer count = null;
		String line;
		final List<IsoContest.Circle> circles = new ArrayList<>();

		try (Scanner sc = new Scanner(input)) {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				/* Lisez les données et effectuez votre traitement */

				if (count == null) {
					count = Integer.parseInt(line);
				} else {
					circles.add(new Circle(line.split("\\s")));
				}
			}
		}

		/*
		 * Vous pouvez aussi effectuer votre traitement une fois que vous avez
		 * lu toutes les données.
		 */
		return circles.parallelStream()
				.map(c -> circles.parallelStream().filter(c1 -> c != c1).map(c1 -> c.intersects(c1)).anyMatch(b -> b))
				.anyMatch(b -> b) ? KO : OK;
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

		System.out.println(contest.solveTopographie(input));
	}
}
