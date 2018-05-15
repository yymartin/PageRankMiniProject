package assignment;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PageRankTask2 {
	private static Scanner scanner = new Scanner(System.in);
	public static Random random = new Random(2013);

	public static void main(String[] argv) {
		/* Pages network */
		int[][] net = { { 1, 2 }, // page 0
				{ 2, 2, 4 }, // page 1
				{ 4 }, // page 2
				{ 0, 0 }, // page 3
				{ 1, 2, 4 } // page 4
		};

		int[] path = randomSurfer(net);
		System.out.println("Pages sequence : "
				+ Arrays.toString(path));
	}

	public static int[] randomSurfer(int[][] net) {
		System.out.println("Enter the number of steps :");
		int steps = scanner.nextInt();

		System.out.println("Enter damping factor :");
		double damping = scanner.nextDouble();
		int[] randomPath = new int[steps];
		randomPath[0] = 0;
		for (int i = 1; i < steps; i++) {
			randomPath[i] = getNextPage(net, randomPath[i - 1], damping);
		}
		return randomPath;
	}

	public static int getNextPage(int[][] net, int currentPage, double damping) {
		int nextPage;

		if (random.nextDouble() <= damping) {
			if (net[currentPage].length == 0) {
				nextPage = random.nextInt(net.length);
			} else {
				nextPage = net[currentPage][random.nextInt(net[currentPage].length)];
			}
		} else {

			nextPage = random.nextInt(net.length);
		}

		if (net[currentPage].length == 0) {
			nextPage = random.nextInt(net.length);
		}
		return nextPage;
	}

}
