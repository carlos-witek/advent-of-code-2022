package aoc.day08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class Day08 {
	public static void main(String[] args) throws FileNotFoundException {
		TreeGrid grid = read();

		leftToRight(grid);
		topToBottom(grid);
		int answer1 = visibleFromEdges(grid);
		System.out.println("Answer1: " + answer1); // 1428881

		System.out.println(grid);
		int answer2 = mostScenic(grid);
		System.out.println("Answer2: " + answer2);

	}

	private static void leftToRight(TreeGrid grid) {
		for (int i = 0; i < grid.size; i++) {
			LinkedList<Tree> row = grid.getRow(i);

			int height = -1;
			ListIterator<Tree> iterator = row.listIterator();

			while (iterator.hasNext()) {
				Tree tree = (Tree) iterator.next();
				if (height < tree.height) {
					tree.visibilityLeft = true;
					height = tree.height;
				}
			}

			height = -1;
			while (iterator.hasPrevious()) {
				Tree tree = (Tree) iterator.previous();
				if (height < tree.height) {
					tree.visibilityRight = true;
					height = tree.height;
				}
			}

		}
	}

	private static void topToBottom(TreeGrid grid) {
		for (int i = 0; i < grid.size; i++) {
			LinkedList<Tree> column = grid.getColumn(i);

			int height = -1;
			ListIterator<Tree> iterator = column.listIterator();

			while (iterator.hasNext()) {
				Tree tree = (Tree) iterator.next();
				if (height < tree.height) {
					tree.visibilityTop = true;
					height = tree.height;
				}
			}

			height = -1;
			while (iterator.hasPrevious()) {
				Tree tree = (Tree) iterator.previous();
				if (height < tree.height) {
					tree.visibilityBottom = true;
					height = tree.height;
				}
			}

		}
	}

	private static int visibleFromEdges(TreeGrid grid) {
		int visibleTreeCount = 0;
		for (int i = 0; i < grid.size; i++) {
			for (int k = 0; k < grid.size; k++) {
				if (grid.getTree(k, i).isVisible()) {
					visibleTreeCount++;
				}
			}
		}
		return visibleTreeCount;
	}

	private static int mostScenic(TreeGrid grid) {
		int mostScenic = 0;
		for (int row = 1; row < grid.size - 1; row++) {
			for (int column = 1; column < grid.size - 1; column++) {

				int height = grid.getTree(column, row).height;

				LinkedList<Tree> rowList = grid.getRow(row);
				int leftIndex = leftIndex(rowList.subList(0, column), height);
				int rightIndex = rightIndex(rowList.subList(column + 1, grid.size), height);
				System.out.println(rowList + " " + leftIndex + ":" + rightIndex);

				LinkedList<Tree> columnList = grid.getColumn(column);
				int topIndex = leftIndex(columnList.subList(0, row), height);
				int bottomIndex = rightIndex(columnList.subList(row + 1, grid.size), height);
				System.out.println(columnList + " " + topIndex + ":" + bottomIndex);

				int nextMostScenic = leftIndex * rightIndex * topIndex * bottomIndex;
				System.out.println(row + ":" + column + " - " + nextMostScenic + " "
						+ Arrays.asList(leftIndex, rightIndex, topIndex, bottomIndex));

				if (nextMostScenic > mostScenic) {
					mostScenic = nextMostScenic;
				}

			}
		}

		return mostScenic;
	}

	private static int leftIndex(List<Tree> list, int height) {
		ListIterator<Tree> listIterator = list.listIterator();
		while (listIterator.hasNext()) {
			listIterator.next();
		}

		int index = 0;
		while (listIterator.hasPrevious()) {
			index++;
			Tree tree = listIterator.previous();
			if (tree.height >= height) {
				break;
			}
		}
		return index;
	}

	private static int rightIndex(List<Tree> list, int height) {
		ListIterator<Tree> listIterator = list.listIterator();

		int index = 0;
		while (listIterator.hasNext()) {
			index++;
			Tree tree = listIterator.next();
			if (tree.height >= height) {
				break;
			}
		}
		return index;
	}

	private static TreeGrid read() throws FileNotFoundException {
		TreeGrid grid = new TreeGrid(99);

		File file = new File("src/aoc/day08/Day08-input.txt");
		try (Scanner scanner = new Scanner(file)) {
			int i = 0;
			while (scanner.hasNextLine()) {
				String[] treeLine = scanner.nextLine().split("");

				for (int k = 0; k < treeLine.length; k++) {
					grid.setTree(k, i, Integer.parseInt(treeLine[k]));
				}
				i++;
			}
		}

		return grid;
	}
}

class TreeGrid {
	public final int size;
	private Tree[][] trees; // row, column

	public TreeGrid(int size) {
		this.size = size;
		this.trees = new Tree[size][size];
	}

	public Tree getTree(int column, int row) {
		return this.trees[row][column];
	}

	public void setTree(int x, int y, int height) {
		this.trees[y][x] = new Tree(height);
	}

	public LinkedList<Tree> getRow(int row) {
		LinkedList<Tree> list = new LinkedList<>();
		for (int i = 0; i < this.size; i++) {
			list.add(trees[row][i]);
		}
		return list;
	}

	public LinkedList<Tree> getColumn(int column) {
		LinkedList<Tree> list = new LinkedList<>();
		for (int i = 0; i < this.size; i++) {
			list.add(trees[i][column]);
		}
		return list;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < this.size; i++) {
			for (int k = 0; k < this.size; k++) {
				Tree tree = trees[i][k];
				if (tree == null) {
					tree = new Tree(0);
				}
				builder.append(tree);
			}
			builder.append("\n");
		}
		return builder.toString();
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
		builder.append("[").append(height);
//		builder.append(":").append(visibilityLeft ? "*" : " ");
//		builder.append(visibilityRight ? "*" : " ");
//		builder.append(visibilityTop ? "*" : " ");
//		builder.append(visibilityBottom ? "*" : " ");
		return builder.append("]").toString();
	}
}
