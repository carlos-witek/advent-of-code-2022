package aoc.day03;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day03 {
	static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) throws FileNotFoundException {
		List<Rucksack> rucksacks = read();

		int sum1 = rucksacks.stream().mapToInt(rucksack -> {
			Set<String> common = rucksack.compartment1();
			common.retainAll(rucksack.compartment2());
			return ALPHABET.indexOf(common.iterator().next()) + 1;
		}).sum();
		System.out.println("Answer1: " + sum1);

		int sum2 = 0;
		Iterator<Rucksack> iterator = rucksacks.iterator();
		while (iterator.hasNext()) {
			Rucksack rucksack1 = iterator.next();
			Rucksack rucksack2 = iterator.next();
			Rucksack rucksack3 = iterator.next();

			Set<String> items1 = rucksack1.items();
			Set<String> items2 = rucksack2.items();
			Set<String> items3 = rucksack3.items();

			items1.retainAll(items2);
			items1.retainAll(items3);

			sum2 += Day03.ALPHABET.indexOf(items1.iterator().next()) + 1;
		}
		System.out.println("Answer2: " + sum2);
	}

	private static List<Rucksack> read() throws FileNotFoundException {
		List<Rucksack> rucksacks = new ArrayList<>();
		File file = new File("src/aoc/day03/Day03.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String string = scanner.nextLine();
			Rucksack rucksack = new Rucksack(string);
			rucksacks.add(rucksack);
		}
		scanner.close();
		return rucksacks;
	}
}

class Rucksack {

	private String string;

	public Rucksack(String string) {
		super();
		this.string = string;
	}

	public Set<String> items() {
		return new HashSet<>(Arrays.asList(string.split("")));
	}

	public Set<String> compartment1() {
		int endIndex = string.length() / 2;
		return new HashSet<>(Arrays.asList(string.substring(0, endIndex).split("")));
	}

	public Set<String> compartment2() {
		int endIndex = string.length() / 2;
		return new HashSet<>(Arrays.asList(string.substring(endIndex).split("")));
	}
}
