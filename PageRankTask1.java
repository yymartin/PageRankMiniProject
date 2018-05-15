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
			System.out.println("L'utilisateur visite la page " + path[i]);
		}
		System.out.println("Visualisation graphique des visites : ");
		for (int i = 0; i < path.length; i++) {
			System.out.println(visualizeVisit(path[i], net.length));
		}
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
		if (random.nextDouble() < 0.9) {
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
