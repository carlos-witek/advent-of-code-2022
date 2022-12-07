package aoc.day07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Day07 {
	public static void main(String[] args) throws FileNotFoundException {
		ElfFile root = read();

		print(root, 1);
		System.out.println();

		int answer1 = sumOfSizesOfFoldersSmallerThan100000(root);
		System.out.println("Answer1: " + answer1); // 1428881

		int freeSpace = 70_000_000 - root.size();
		Optional<ElfFile> folders = folderToDelete(root, 1, freeSpace).stream()
				.min((a, b) -> Integer.compare(a.size(), b.size()));
		System.out.println("Answer2: " + folders.get().size());

	}

	private static void print(ElfFile file, int level) {
		for (int i = 0; i < 11 - level; i++) {
			System.out.print(" ");
		}
		for (int i = 0; i < level; i++) {
			System.out.print("-");
		}
		System.out.println(String.format(" %20s (%s) %d", file.name, (file.size == 0 ? "d" : " "), file.size()));

		for (ElfFile x : file.children) {
			print(x, level + 1);
		}
	}

	private static int sumOfSizesOfFoldersSmallerThan100000(ElfFile file) {
		int size = file.size();

		int sumOfSizes = 0;
		if (size <= 100_000 && file.size == 0) {
			sumOfSizes += size;
		}

		for (ElfFile child : file.children) {
			sumOfSizes += sumOfSizesOfFoldersSmallerThan100000(child);
		}

		return sumOfSizes;
	}

	private static List<ElfFile> folderToDelete(ElfFile file, int level, int freeSpace) {
		int size = file.size();

		List<ElfFile> forDeletion = new ArrayList<>();
		if (freeSpace + size > 30_000_000) {
			forDeletion.add(file);
		}

		for (ElfFile a : file.children) {
			forDeletion.addAll(folderToDelete(a, level + 1, freeSpace));
		}

		return forDeletion;
	}

	private static ElfFile read() throws FileNotFoundException {
		ElfFile root = new ElfFile(null, "/");

		File file = new File("src/aoc/day07/Day07.txt");
		try (Scanner scanner = new Scanner(file)) {
			if (scanner.hasNextLine()) {
				scanner.nextLine();
			}

			ElfFile folder = root;

			while (scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();

				if (nextLine.startsWith("$ cd ..")) {
					folder = folder.parent;
				} else if (nextLine.startsWith("$ cd")) {
					String name = nextLine.substring(5);
					folder = new ElfFile(folder, name);
				} else if (nextLine.startsWith("$ ls")) {
				} else if (nextLine.startsWith("dir")) {
				} else {
					String[] split = nextLine.split(" ");
					int size = Integer.parseInt(split[0]);
					new ElfFile(folder, split[1], size);
				}
			}
			return root;
		}
	}
}

class ElfFile {
	public final ElfFile parent;
	public final List<ElfFile> children = new ArrayList<>();
	public final String name;
	public final int size;

	public ElfFile(ElfFile parent, String name) {
		this(parent, name, 0);
	}

	public ElfFile(ElfFile parent, String name, int size) {
		this.parent = parent;
		if (this.parent != null) {
			this.parent.addChild(this);
		}
		this.name = name;
		this.size = size;
	}

	private void addChild(ElfFile elfFile) {
		this.children.add(elfFile);
	}

	public int size() {
		return size + children.stream().mapToInt(ElfFile::size).sum();
	}
}
