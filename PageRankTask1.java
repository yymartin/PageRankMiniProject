package assignment;

import java.util.Random;

public class PageRankTask1 {
	public static Random random = new Random(2013);
	public static void main(String[] argv) {
		int[][] net = { 
				{ 1, 2 }, // page 0
				{ 2, 2, 4 }, // page 1
				{ 4 }, // page 2
				{ 0, 0 }, // page 3
				{ 1, 2, 4 } // page 4
		};

		int[] path = randomSurfer(net, 10);
		for (int i = 0; i < path.length; i++) {
			System.out.println("User visits page " + path[i]);
		}
		System.out.println("Graphical visualisation of visits : ");
		for (int i = 0; i < path.length; i++) {
			System.out.println(visualizeVisit(path[i], net.length));
		}
	}

	public static int[] randomSurfer(int[][] net, int steps) {
		int[] randomPath = new int[steps];
		randomPath[0] = 0;
		for (int i = 1; i < steps; i++) {
			randomPath[i] = getNextPage(net, randomPath[i - 1]);
		}
		return randomPath;
	}

	public static int getNextPage(int[][] net, int currentPage) {
		int nextPage;
		// This permits to obtain the damping factor by using random Double
		// First case, the user decides to click on a link
		if (random.nextDouble() < 0.9) {
			// Case where actual page has no link
			if (net[currentPage].length == 0) {
				nextPage = random.nextInt(net.length);
			} else {
				nextPage = net[currentPage][random
						.nextInt(net[currentPage].length)];
			}
		} else {
			// Second case, the user types an url
			nextPage = random.nextInt(net.length);
		}
		return nextPage;
	}

	public static String visualizeVisit(int page, int totalPageNum) {
		String visual = "";
		for (int i = 0; i < totalPageNum; i++) {
			if (i == page) {
				visual += 'x';
			} else {
				visual += '-';
			}
		}
		return visual;
	}
}
