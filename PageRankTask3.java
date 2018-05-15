package assignment;

import java.util.Random;

public class PageRankTask3 {
	public static Random random = new Random(2013);
	public static final double[] realPageranks = new double[] {
			0.03800000000000003, 0.17960674190896989, 0.2873707867385369,
			0.02000000000000001, 0.47502247135249404 };
	/*
	 * En modifiant le nombre de pages qui pointent vers 3, on augmente son
	 * PageRank et ainsi on augmente le PageRank de 3
	 */
	public static int[][] netpage0= 
		{ { 1, 2, 3 }, // page 0
		{ 2, 2, 4, 3 }, // page 1
		{ 4, 3 }, // page 2
		{ 0, 0, 3 }, // page 3
		{ 1, 2, 4, 3 } // page 4
};

	public static void main(String[] argv) {
		/* Réseau de pages exemple */
		int[][] net = { { 1, 2 }, // page 0
				{ 2, 2, 4 }, // page 1
				{ 4 }, // page 2
				{ 0, 0 }, // page 3
				{ 1, 2, 4 } // page 4
		};

		int[] path = randomSurfer(net, 20);
		double[] ranks = computePageRank(path, net.length);

		for (int p = 0; p < ranks.length; p++) {
			System.out.print("La page " + p + " a été visitée "
					+ countVisit(path, p) + " fois. ");
			System.out.println("Son PageRank estimé est de " + ranks[p]);
		}

		System.out.println("Il faut " + getConvSteps(net)
				+ " itérations pour avoir un résultat précis");
	}

	// OK(Hugo)
	public static int[] randomSurfer(int[][] net, int steps) {
		int[] randomPath = new int[steps];
		randomPath[0] = 0;
		for (int i = 1; i < steps; i++) {
			randomPath[i] = getNextPage(net, randomPath[i - 1]);
		}
		return randomPath;
	}

	// OK(Hugo)
	public static int getNextPage(int[][] net, int currentPage) {
		int nextPage;

		// Ceci permet d'obtenir le facteur de damping en utilisant un Double
		// aleatoire
		// Ici premiere solution , le surfer decide de cliquer sur un lien
		if (random.nextDouble() <= 0.9) {
			// ici on code le cas ou la page actuelle n'a aucun lien sortant
			if (net[currentPage].length == 0) {
				nextPage = random.nextInt(net.length);
			} else {
				nextPage = net[currentPage][random
						.nextInt(net[currentPage].length)];
			}
		} else {
			// Ici deuxieme solution le surfer decide de taper lui meme une
			// addresse url
			nextPage = random.nextInt(net.length);
		}
		return nextPage;
	}

	// OK(Hugo)
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
			// permet d'obtenir la valeur absolue de la difference des valeurs
			// de chaque tableau
			absDiff = Math.abs(rank1[i] - rank2[i]);

			// permet d'obtenir le maximum de la difference entre deux valeurs
			// de chacun des tableaux pour une meme page
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

	// OK (Hugo)
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
