package assignment;

import java.util.Arrays;
import java.util.Random;

public class PageRankTask4 {

	/*
	 * Question 4.d : Which method requires the smallest number of iterations to get 
	 * a precise PageRank?
	 * 
	 * The task2 method requires 2787 iterations. The matrix method requires 25 iterations.
	 * Hence, the matrix method is better.
	 * 
	 * Why?
	 *
	 * The transition matrix is a probability matrix containing the probability that
	 * a page (i,j) is accessed from page 0. 25 iterations is required to make the result
	 * more precise. On the other hand, the naive method of task2 generates a random path
	 * and deduces the corresponding probability. Consequently, the number of random paths
	 * generated must be very big to represent reality.
	 */

	public static Random random = new Random(2013);

	public static void main(String[] argv) {
		/* Pages network */
		int[][] net = { { 1, 2 }, // page 0
				{ 2, 2, 4 }, // page 1
				{ 4 }, // page 2
				{ 0, 0 }, // page 3
				{ 1, 2, 4 } // page 4
		};

		System.out
				.println("PageRank estimation (random walk - 25 iterations - damping 0.9) : ");
		int[] path = PageRankTask1.randomSurfer(net, 25);
		System.out.println(Arrays.toString(PageRankTask3.computePageRank(path,
				net.length)));

		System.out
				.println("PageRand estimation (matrix method - 25 iterations - damping 0.9 : ");
		System.out.println(Arrays.toString(estimatePageRank(net, 25, 0.9)));
	}

	public static double[] estimatePageRank(int[][] net, int steps,
			double dampingFactor) {
		double[] matricePageRank = new double[net.length];
		matricePageRank = pageRankIterations(
				computeTransitionsMatrix(net, dampingFactor), steps);
		return matricePageRank;
	}

	public static double[][] computeTransitionsMatrix(int[][] net,
			double dampingFactor) {
		double[][] matrice1 = new double[net.length][net.length];
		for (int i = 0; i < matrice1.length; i++) {
			for (int j = 0; j < matrice1.length; j++) {
				matrice1[i][j] = 0.0;
			}
		}

		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net.length; j++) {
				for (int k = 0; k < net[i].length; k++) {
					if (net[i][k] == j) {
						matrice1[i][j] += 1;
					}
				}
			}
		}

		double[][] matrice2 = new double[net.length][net.length];
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net.length; j++) {
				matrice2[i][j] = (dampingFactor * matrice1[i][j] / net[i].length)
						+ ((1 - dampingFactor) / net.length);
			}
		}
		return matrice2;
	}

	public static double[] pageRankIterations(double[][] transitions, int steps) {
		double[][] matrice1 = new double[1][transitions.length];
		matrice1[0][0] = 1.0;
		for (int i = 1; i < matrice1[0].length; i++) {
			matrice1[0][i] = 0.0;
		}

		for (int s = 0; s < steps; s++) {
			matrice1 = multiplierMatrice(matrice1, transitions);
		}

		double[] matriceFinale = new double[matrice1[0].length];
		for (int i = 0; i < matrice1[0].length; i++) {
			matriceFinale[i] = matrice1[0][i];
		}
		return matriceFinale;
	}

	public static double[][] multiplierMatrice(double[][] m1, double[][] m2) {
		double[][] mat = new double[m1.length][m2[0].length];
		double tmp = 0;
		int i = 0;
		int j = 0;
		for (i = 0; i < m1.length; i++) {
			for (j = 0; j < m2[0].length; j++) {
				tmp = 0;
				for (int k = 0; k < m1[0].length; k++) {
					tmp += m1[i][k] * m2[k][j];
				}
				mat[i][j] = tmp;
			}
		}

		return mat;
	}
}