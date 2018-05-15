package assignment;

import java.util.Arrays;
import java.util.Random;

public class PageRankTask4 {

	/*
	 * Réponses à la question de la tâche 4.d : - Quelle méthode nécessite le
	 * moins d'itérations pour obtenir un PageRank précis ? - Réponse :
	 * 
	 * La methode de task2 necessite 2787 iterations pour obtenir le meme
	 * resultat que la methode matricielle apres 25 iterations, donc la methode
	 * matricielle necessite beaucoup moins d'iterations pour une meme
	 * precision.
	 * 
	 * - Pourquoi, selon vous ? - Réponse :
	 * 
	 * Car plutot que de generer un surfer aleatoire qui se deplacerait sur les
	 * pages la methode matricielle permet a travers la matrice de transition
	 * d'associer a chaque page sa probabilite d'etre atteinte depuis la page 0,
	 * les 25 iterations permettant d'affiner le resultat. Tandis que la methode
	 * "naive" de la Task2, genere un chemin aleatoire et en deduit des
	 * probabilités: c'est a dire qu'il faut un tres grand nombre de pas pour
	 * que les probabilités deduites soient prochent de la réalité.Cela est
	 * comparable a essayer d'obtenir la probabilité de chaque face d'une pièce
	 * en la lancant en l'air, il faut reiterer l'experience plusieurs fois afin
	 * d'obtenir les probabilitées réelles.
	 */

	/* Utilisez cet objet pour générer des nombres aléatoires */
	public static Random random = new Random(2013);

	public static void main(String[] argv) {
		/* Réseau de pages exemple */
		int[][] net = { { 1, 2 }, // page 0
				{ 2, 2, 4 }, // page 1
				{ 4 }, // page 2
				{ 0, 0 }, // page 3
				{ 1, 2, 4 } // page 4
		};

		System.out
				.println("Estimation du PageRank (random walk - 25 itérations - damping 0.9) : ");
		int[] path = PageRankTask1.randomSurfer(net, 25);
		System.out.println(Arrays.toString(PageRankTask3.computePageRank(path,
				net.length)));

		System.out
				.println("Estimation du PageRank (méthode matricielle) - 25 itérations - damping 0.9 : ");
		System.out.println(Arrays.toString(estimatePageRank(net, 25, 0.9)));
	}

	// OK (Hugo)
	public static double[] estimatePageRank(int[][] net, int steps,
			double dampingFactor) {
		double[] matricePageRank = new double[net.length];
		matricePageRank = pageRankIterations(
				computeTransitionsMatrix(net, dampingFactor), steps);
		return matricePageRank;
	}

	// OK (Hugo)
	public static double[][] computeTransitionsMatrix(int[][] net,
			double dampingFactor) {

		// d'abord on cree la matrice qui contient le nombre de liens d'une page
		// vers une autre (A dans l'exemple)
		double[][] matrice1 = new double[net.length][net.length];
		// On remplit notre matrice de zeros pour eviter de se servir d'une
		// variable compteur
		for (int i = 0; i < matrice1.length; i++) {
			for (int j = 0; j < matrice1.length; j++) {
				matrice1[i][j] = 0.0;
			}
		}

		// On parcourt toutes les cases de la matrice
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net.length; j++) {

				// k joue le role de variable pour parcourir les pages
				// referencées par la page i
				for (int k = 0; k < net[i].length; k++) {

					// ici, si jamais la page i contient un lien vers j on
					// ajoute un dans la case correpondante de la matrice
					if (net[i][k] == j) {
						matrice1[i][j] += 1;
					}
				}
			}
		}
		// -----------------------------------------------------------------------
		// Puis on code la matrice de transition P
		double[][] matrice2 = new double[net.length][net.length];
		for (int i = 0; i < net.length; i++) {
			for (int j = 0; j < net.length; j++) {
				matrice2[i][j] = (dampingFactor * matrice1[i][j] / net[i].length)
						+ ((1 - dampingFactor) / net.length);
			}
		}
		return matrice2;
	}

	// OK (Hugo)
	public static double[] pageRankIterations(double[][] transitions, int steps) {
		// comme le precise l'enonce on cree une matrice remplie de 0 sauf pour
		// sa premiere coordonee
		double[][] matrice1 = new double[1][transitions.length];
		matrice1[0][0] = 1.0;
		for (int i = 1; i < matrice1[0].length; i++) {
			matrice1[0][i] = 0.0;
		}

		// ici on appelle la matrice transition pour faire la multiplication
		// matricielle(on utiliser la methode multiplierMatrice)
		for (int s = 0; s < steps; s++) {
			matrice1 = multiplierMatrice(matrice1, transitions);
		}

		// on doit encore convertir notre matrice 1xn en tableau à une dimension
		// de longueur n
		double[] matriceFinale = new double[matrice1[0].length];
		for (int i = 0; i < matrice1[0].length; i++) {
			matriceFinale[i] = matrice1[0][i];
		}
		return matriceFinale;
	}

	// OK (Hugo)
	static double[][] multiplierMatrice(double[][] m1, double[][] m2) {
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