package aoc.day04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day04 {
	static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) throws FileNotFoundException {
		List<Assignment> assignments = read();

		int sum1 = assignments.stream().mapToInt(assignment -> assignment.contains()).sum();
		System.out.println("Answer1: " + sum1);

		int sum2 = assignments.stream().mapToInt(assignment -> assignment.overlap()).sum();
		System.out.println("Answer2: " + sum2);
	}

	private static List<Assignment> read() throws FileNotFoundException {
		List<Assignment> assignments = new ArrayList<>();
		File file = new File("src/aoc/day04/Day04.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String string = scanner.nextLine();
			String[] ranges = string.split(",");

			String[] range1Strings = ranges[0].split("-");
			Range range1 = new Range(Integer.parseInt(range1Strings[0]), Integer.parseInt(range1Strings[1]));
			String[] range2Strings = ranges[1].split("-");
			Range range2 = new Range(Integer.parseInt(range2Strings[0]), Integer.parseInt(range2Strings[1]));

			Assignment assignment = new Assignment(range1, range2);
			assignments.add(assignment);
		}
		scanner.close();
		return assignments;
	}
}

class Range {
	public final int start;
	public final int end;

	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public boolean contains(Range range) {
		return start <= range.start && range.end <= end;
	}

	public boolean overlap(Range range) {
		return (start <= range.start && range.start <= end) || (start <= range.end && range.end <= end);
	}
}

class Assignment {
	private Range left;
	private Range right;

	public Assignment(Range left, Range right) {
		this.left = left;
		this.right = right;
	}

	public int contains() {
		if (this.left.contains(this.right) || this.right.contains(this.left)) {
			return 1;
		}
		return 0;
	}

	public int overlap() {
		if (this.left.overlap(this.right) || this.right.overlap(this.left)) {
			return 1;
		}
		return 0;
	}
}
