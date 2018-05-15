package assignment;

import java.util.Random;

public class PageRankTask3 {
	public static Random random = new Random(2013);
	public static final double[] realPageranks = new double[] {
			0.03800000000000003, 0.17960674190896989, 0.2873707867385369,
			0.02000000000000001, 0.47502247135249404 };
	/*
	 * By modifying the number of pages pointing to page 3, we increse its PageRank
	 */
	public static int[][] netpage0= 
		{ { 1, 2, 3 }, // page 0
		{ 2, 2, 4, 3 }, // page 1
		{ 4, 3 }, // page 2
		{ 0, 0, 3 }, // page 3
		{ 1, 2, 4, 3 } // page 4
};

	public static void main(String[] argv) {
		/* Pages network */
		int[][] net = { { 1, 2 }, // page 0
				{ 2, 2, 4 }, // page 1
				{ 4 }, // page 2
				{ 0, 0 }, // page 3
				{ 1, 2, 4 } // page 4
		};

		int[] path = randomSurfer(net, 20);
		double[] ranks = computePageRank(path, net.length);

		for (int p = 0; p < ranks.length; p++) {
			System.out.print("Page " + p + " visited "
					+ countVisit(path, p) + " times. ");
			System.out.println("Its PageRank estimated : " + ranks[p]);
		}

		System.out.println("We need " + getConvSteps(net)
				+ " iterations to get a precise result");
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
		if (random.nextDouble() <= 0.9) {
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

	public static double[] computePageRank(int[] path, int pageCount) {
		double[] tabPageRank = new double[pageCount];
		for (int i = 0; i <= (pageCount - 1); i++) {
			tabPageRank[i] = (countVisit(path, i) / (double) path.length);
		}

		return tabPageRank;
	}

	public static double getMaxDiff(final double[] rank1, final double[] rank2) {
		double maxDiff = 0.0;
		double absDiff;

		for (int i = 0; i < rank1.length; i++) {
			absDiff = Math.abs(rank1[i] - rank2[i]);
			if (absDiff >= maxDiff) {
				maxDiff = absDiff;
			}
		}
		return maxDiff;
	}

	public static int getConvSteps(final int[][] net) {
		int k = 1;
		double maxDiff = 1;
		 while (maxDiff >= 0.001) {
			int[] path = randomSurfer(net, k);
			double[] tabPageRank = computePageRank(path, net.length);
			maxDiff = getMaxDiff(tabPageRank, realPageranks);
			++k;
		};

		return (k-1);
	}

	public static int countVisit(int[] path, int page) {
		int nbVisitePage = 0;
		for (int i = 0; i < path.length; i++) {
			if (path[i] == page) {
				nbVisitePage += 1;
			}
		}
		return nbVisitePage;
	}
}
