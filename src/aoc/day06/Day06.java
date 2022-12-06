package aoc.day06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day06 {
	public static void main(String[] args) throws FileNotFoundException {
		List<String> strings = read();

		int marker4 = findMarker(strings, 4);
		System.out.println("Answer1: " + marker4);

		int marker14 = findMarker(strings, 14);
		System.out.println("Answer2: " + marker14);

	}

	private static int findMarker(List<String> strings, int length) {
		int index = 1;
		LinkedList<String> latest = new LinkedList<>();
		for (String string : strings) {
			if (latest.size() == length) {
				latest.removeFirst();
			}
			latest.addLast(string);

			if (new HashSet<>(latest).size() == length) {
				break;
			}

			index++;
		}
		return index;
	}

	private static List<String> read() throws FileNotFoundException {
		File file = new File("src/aoc/day06/Day06.txt");
		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				return Arrays.asList(scanner.nextLine().split(""));
			}
		}
		throw new IllegalStateException();
	}
}
