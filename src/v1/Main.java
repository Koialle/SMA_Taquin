
package v1;

import java.util.Scanner;

/**
 *
 * @author Epulapp
 */
public class Main {
    public static void main(String args[])
    {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        
        int tailleTaquin, nbAgents, nbAgentsMax;
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Taille du taquin (< 5): ");

        tailleTaquin = scanner.nextInt();
        nbAgentsMax = tailleTaquin*tailleTaquin - 1;

        do {
            System.out.printf("Nombre d'agents (<= %d): ", nbAgentsMax);
            nbAgents = scanner.nextInt();
        } while (nbAgents > nbAgentsMax);
        
        System.out.println("Initialisation de la grille");
        Grille.setSize(5);
        for (int i = 0; i < nbAgents; i++) {
            
            String symbole = String.valueOf(alphabet[i % 26]);
//            System.out.println(symbole);
            
//            if (i/26 > 0) {
//                System.out.println(i/26);
//                symbole += i/26;
//            }

            Agent agent = new Agent(symbole);
            
            Grille.add(agent);
        }

        System.out.println("Grille initiale");
        Grille.afficher();
        
        System.out.println("Grille objectif");
        Grille.afficherObjectifs();

        for (Agent agent : Grille.getAgents().values()) {
            agent.start();
        }
    }
}
