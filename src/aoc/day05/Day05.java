package aoc.day05;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day05 {
	public static void main(String[] args) throws FileNotFoundException {
		List<Instruction> instructions = readInstructions();

		{
			List<Stack> stacks = readStacks();
			for (Instruction instruction : instructions) {
				instruction.execute9000(stacks);
			}
			StringBuilder builder = new StringBuilder();
			for (Stack stack : stacks) {
				builder.append(stack.elements.getLast());
			}
			System.out.println("Answer1: " + builder.toString());
		}

		{
			List<Stack> stacks = readStacks();
			for (Instruction instruction : instructions) {
				instruction.execute9001(stacks);
			}
			StringBuilder builder = new StringBuilder();
			for (Stack stack : stacks) {
				builder.append(stack.elements.getLast());
			}
			System.out.println("Answer2: " + builder.toString());
		}
	}

	private static List<Stack> readStacks() throws FileNotFoundException {
		List<Stack> stacks = new ArrayList<>();

		File file = new File("src/aoc/day05/Day05-stack.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String[] elements = scanner.nextLine().split("");

			Stack stack = new Stack();
			for (String element : elements) {
				stack.elements.add(element);
			}

			stacks.add(stack);
		}
		scanner.close();

		return stacks;
	}

	private static List<Instruction> readInstructions() throws FileNotFoundException {
		List<Instruction> instructions = new ArrayList<>();

		File file = new File("src/aoc/day05/Day05-moves.txt");
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String[] parts = scanner.nextLine().split(" ");

			Instruction instruction = new Instruction(Integer.parseInt(parts[1]),
					Integer.parseInt(parts[3]),
					Integer.parseInt(parts[5]));

			instructions.add(instruction);
		}
		scanner.close();

		return instructions;
	}
}

class Stack {
	public final LinkedList<String> elements;

	public Stack() {
		this.elements = new LinkedList<>();
	}
}

class Instruction {
	private int count;
	private int from;
	private int to;

	public Instruction(int count, int from, int to) {
		this.count = count;
		this.from = from;
		this.to = to;
	}

	public void execute9000(List<Stack> stacks) {
		Stack toStack = stacks.get(to - 1);
		Stack fromStack = stacks.get(from - 1);

		for (int i = 0; i < count; i++) {
			toStack.elements.addLast(fromStack.elements.removeLast());
		}
	}

	public void execute9001(List<Stack> stacks) {
		Stack toStack = stacks.get(to - 1);
		Stack fromStack = stacks.get(from - 1);

		int size = toStack.elements.size();
		toStack.elements.addLast(fromStack.elements.removeLast());
		for (int i = 1; i < count; i++) {
			toStack.elements.add(size, fromStack.elements.removeLast());
		}
	}
}
