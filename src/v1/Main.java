
package v1;

/**
 *
 * @author Epulapp
 */
public class Main {
    public static void main(String args[])
    {
        Agent agent1 = new Agent("X");
        Agent agent2 = new Agent("Y");
        Agent agent3 = new Agent("A");
        Agent agent4 = new Agent("M");

        Grille.setSize(5);
        Grille.add(agent1);
        Grille.add(agent2);
        Grille.add(agent3);
        Grille.add(agent4);
        
        System.out.println("Grille initiale");
        Grille.afficher();
        
        System.out.println("Grille objectif");
        Grille.afficherObjectifs();
        
        agent1.start();
        agent2.start();
        agent3.start();
        agent4.start();
    }
}
