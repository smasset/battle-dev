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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class IsoContest {

	private static final String OK = "OK";

	private static final String KO = "KO";

	private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#.###",
			DecimalFormatSymbols.getInstance(Locale.US));

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
		protected Position center = null;
		protected Integer radius = null;

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

		public boolean contains(Circle other) {
			Double distance = this.center.getDistanceTo(other.center);
			return distance < Math.abs(this.radius.doubleValue() - other.radius.doubleValue())
					&& other.radius < this.radius;
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

	private class Summit {
		private Integer id;
		private Map<Integer, Double> risks;

		public Summit(Integer id, Set<Entry<Integer, Double>> risks) {
			this.id = id;
			this.risks = risks.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		}

		public Summit(Integer id, String... params) {
			this.id = id;
			this.risks = new HashMap<>(params.length - 1);

			for (int summitIndex = 0; summitIndex < params.length; ++summitIndex) {
				try {
					if (summitIndex != id) {
						this.risks.put(summitIndex, DOUBLE_FORMAT.parse(params[summitIndex]).doubleValue());
					}
				} catch (ParseException e) {
					throw new IllegalArgumentException(
							String.format("Can't parse data %s to double", params[summitIndex]), e);
				}
			}
		}
	}

	private class DoubleEntryComparator implements Comparator<Entry<Integer, Double>> {

		@Override
		public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
			return o1.getValue().compareTo(o2.getValue());
		}
	}

	private Double findSafestPath(Integer from, Integer to, List<IsoContest.Summit> summits, Double temp) {
		Double value;

		Map<Integer, Double> risksTo = summits.stream().filter(s -> s.id.equals(to)).findFirst().get().risks;
		Double shortestPath = risksTo.get(from);

		Optional<Entry<Integer, Double>> safestSummit = risksTo.entrySet().stream()
				.filter(entry -> entry.getKey() != from).min(new DoubleEntryComparator());

		if (safestSummit.isPresent()) {
			value = findSafestPath(from, safestSummit.get().getKey(),
					summits.stream().filter(s -> !s.id.equals(to))
							.map(s -> new IsoContest.Summit(s.id,
									s.risks.entrySet().stream().filter(e -> !e.getKey().equals(to))
											.collect(Collectors.toSet())))
							.collect(Collectors.toList()),
					temp * (1 - safestSummit.get().getValue()));
		} else {
			value = temp * (1 - shortestPath);
		}

		return value;
	}

	public String solveAvalanches(InputStream input) {
		Integer count = null;
		Integer from = null;
		Integer to = null;

		String line;
		List<IsoContest.Summit> summits = null;

		try (Scanner sc = new Scanner(input)) {
			Integer summitId = 0;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				/* Lisez les données et effectuez votre traitement */

				if (count == null) {
					count = Integer.parseInt(line);
					summits = new ArrayList<>(count);
				} else if (from == null) {
					String[] temp = line.split("\\s");

					from = Integer.parseInt(temp[0]);
					to = Integer.parseInt(temp[1]);
				} else {
					summits.add(new Summit(summitId++, line.split("\\s")));
				}
			}
		}

		return DOUBLE_FORMAT
				.format(Math.min(summits.get(from).risks.get(to), 1 - this.findSafestPath(from, to, summits, 1.0d)));
	}

	private class Disk extends Circle implements Comparable<Disk> {
		private Integer id = null;
		private Integer height = null;
		private SortedSet<Disk> path = null;
		private Integer score = null;

		public Disk(Integer id, String... params) {
			super(params);
			this.id = id;
			this.height = Integer.valueOf(params[3]);
			this.path = new TreeSet<>(new Comparator<Disk>() {

				@Override
				public int compare(Disk o1, Disk o2) {
					return o1.radius.compareTo(o2.radius);
				}
			});
		}

		private Integer getHeightDifference(Disk other) {
			return Math.abs(height - other.height);
		}

		public Integer getScore() {
			if (score == null) {
				score = Math.abs(this.path.isEmpty() ? this.height : this.path.last().height);
				Disk previousDisk = this;

				for (Disk currentDisk : this.path) {
					score += currentDisk.getHeightDifference(previousDisk);
					previousDisk = currentDisk;
				}
			}

			return score;
		}

		public boolean contains(Disk other) {
			return this.id != other.id && super.contains(other);
		}

		@Override
		public boolean equals(Object obj) {
			return this.id.equals(((Disk) obj).id);
		}

		@Override
		public int compareTo(Disk o) {
			return o.getScore().compareTo(this.getScore());
		}

		@Override
		public String toString() {
			return String.format("%d %s %d %d", id, super.toString(), height, this.getScore());
		}
	}

	private Integer getCoolestPath(List<Disk> disks) {
		disks.forEach(d1 -> {
			d1.path.addAll(disks.parallelStream().filter(d2 -> d2.contains(d1)).collect(Collectors.toList()));
		});

		PriorityQueue<Disk> queue = new PriorityQueue<>(disks);
		Disk out = queue.poll();
		Disk lastOut = out.path.isEmpty() ? out : out.path.last();
		queue.removeIf(d -> d.path.contains(lastOut));

		return out.getScore() + queue.poll().getScore();
	}

	public String solveSnowboarding(InputStream input) {
		Integer count = null;
		Integer id = 0;
		String line = null;
		List<Disk> disks = null;

		try (Scanner sc = new Scanner(input)) {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				/* Lisez les données et effectuez votre traitement */

				if (count == null) {
					count = Integer.parseInt(line);
					disks = new ArrayList<>(count);
				} else {
					disks.add(new Disk(id++, line.split("\\s")));
				}
			}
		}

		return this.getCoolestPath(disks).toString();
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

		System.out.println(contest.solveSnowboarding(input));
	}
}
