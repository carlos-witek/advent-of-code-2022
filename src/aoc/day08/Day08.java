package aoc.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day08 {
	public static void main(String[] args) throws FileNotFoundException {
		Tree[][] trees = read();

		visibilityFromLeft(trees);
		visibilityFromRight(trees);
		visibilityFromTop(trees);
		visibilityFromBottom(trees);
		int answer1 = countVisibleFromEdges(trees);

		System.out.println("Answer1: " + answer1); // 1428881

		System.out.println("Answer2: " + 0);

	}

	private static void visibilityFromLeft(Tree[][] trees) {
		for (int y = 0; y < trees.length; y++) {
			int height = -1;
			for (int x = 0; x < trees[y].length; x++) {
				Tree tree = trees[y][x];
				if (tree != null) {
					if (height < tree.height) {
						tree.visibilityLeft = true;
						height = tree.height;
					}
				}
			}
		}
	}

	private static void visibilityFromRight(Tree[][] trees) {
		for (int y = 0; y < trees.length; y++) {
			int height = -1;
			for (int x = trees[y].length - 1; 0 <= x; x--) {
				Tree tree = trees[y][x];
				if (tree != null) {
					if (height < tree.height) {
						tree.visibilityRight = true;
						height = tree.height;
					}
				}
			}
		}
	}

	private static void visibilityFromTop(Tree[][] trees) {
		int size = trees.length;
		for (int x = 0; x < size; x++) {
			int height = -1;
			for (int y = 0; y < size; y++) {
				Tree tree = trees[y][x];
				if (tree != null) {
					if (height < tree.height) {
						tree.visibilityTop = true;
						height = tree.height;
					}
				}
			}
		}
	}

	private static void visibilityFromBottom(Tree[][] trees) {
		int size = trees.length;
		for (int x = 0; x < size; x++) {
			int height = -1;
			for (int y = size - 1; 0 <= y; y--) {
				Tree tree = trees[y][x];
				if (tree != null) {
					if (height < tree.height) {
						tree.visibilityBottom = true;
						height = tree.height;
					}
				}
			}
		}
	}

	private static int countVisibleFromEdges(Tree[][] trees) {
		int visible = 0;
		for (int i = 0; i < trees.length; i++) {
			for (int k = 0; k < trees[i].length; k++) {
				if (trees[i][k] != null) {
					System.out.print(trees[i][k]);
					if (trees[i][k].isVisible()) {
						visible++;
					}
				}
			}
			System.out.println();
		}
		return visible;
	}

	private static int mostScenic(Tree[][] trees) {
		for (int y = 0; y < trees.length; y++) {
			for (int x = 0; x < trees[y].length; x++) {
				Tree tree = trees[y][x];
				int treeScore = 0;
				if (tree == null)
					continue;

				// check left
				int xis = 1;
				for (int xi = x - 1; 0 <= xi; xi--) {

				}
				treeScore *= xis;

			}
		}

		return 0;
	}

	private static Tree[][] read() throws FileNotFoundException {
		Tree[][] trees = new Tree[6][6];

		File file = new File("src/aoc/day08/Day08-example.txt");
		try (Scanner scanner = new Scanner(file)) {
			int i = 0;
			while (scanner.hasNextLine()) {
				String[] treeLine = scanner.nextLine().split("");

				for (int k = 0; k < treeLine.length; k++) {
					trees[i][k] = new Tree(Integer.parseInt(treeLine[k]));
				}
				i++;
			}
		}

		return trees;
	}
}

class Tree {
	public final int height;
	public boolean visibilityLeft;
	public boolean visibilityRight;
	public boolean visibilityTop;
	public boolean visibilityBottom;

	public Tree(int height) {
		this.height = height;
	}

	public boolean isVisible() {
		return visibilityLeft || visibilityRight || visibilityTop || visibilityBottom;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(height).append("]");
		builder.append(visibilityLeft ? "*" : " ");
		builder.append(visibilityRight ? "*" : " ");
		builder.append(visibilityTop ? "*" : " ");
		builder.append(visibilityBottom ? "*" : " ");
		return builder.toString();
	}
}
