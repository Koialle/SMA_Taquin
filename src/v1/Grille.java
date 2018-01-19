
package v1;

import java.util.HashMap;
import java.util.Map;
import v1.exception.PositionUsedException;

/**
 *
 * @author Mélanie
 * @author Ophélie
 * 
 * @TODO rename Takin
 */
public class Grille {
    private static int size;
    private static Map<Position, Agent> agents = new HashMap<>();
    private static Map<Position, Agent> objectifs = new HashMap<>();

    private Grille() {
        size = 10;
        agents = new HashMap<>();
        objectifs = new HashMap<>();
    }

    public static Agent get(int x, int y) {
        return get(new Position(x, y));
    }

    public static Agent get(Position position) {
        return agents.get(position);
    }

    public static void add(Agent agent) {
        Position position, objectif;
        
        // set an initial position
        do {
            position = new Position(size);
        } while (agents.get(position) != null);
        
        // Set an objectif
        do {
            objectif = new Position(size);
        } while (objectifs.get(objectif) != null);
        
        agent.setPosition(position);
        agent.setObjectif(objectif);

        agents.put(position, agent);
        objectifs.put(objectif, agent);

        System.out.printf("Agent %s ajouté à la position (%d, %d)\n", agent.getSymbole(), position.getX(), position.getY());
    }
    
    /**
     * Find a coherent position around the current position of the agent,
     * used when the agent is asked to move by another agent.
     *
     * @param agent
     * @return A position
     */
    public synchronized static Position move(Agent agent) {
        Position position = agent.getPosition();
        
        for (int i = -1; i <= 1; i++) {
            for (int y = -1; y < 1; y++) {
                if (y != i && y != -i) { // n'est pas une diagonale
                    int newX = position.getX() + i;
                    int newY = position.getY() + y;
                    
                    if (get(newX, newY) == null) {
                        return new Position(newX, newY);
                    }
                }
            }
        }
        
        return null;
    }
    
    /**
     * Return true if all agents have reached their objectives.
     * 
     * @return 
     */
    public synchronized static boolean isSatisfied()
    {
        //@TODO check is agents at their objective position
        for (Agent agent: agents.values()) {
            if (!agent.getPosition().equals(agent.getObjectif())) {
                return false;
            }
        }
        
        return true;
    }

    public synchronized static void move(Agent agent, Position position) throws Exception {
        if (agents.get(position) != null) {
            throw new PositionUsedException(agents.get(position));
        } else {
            Position old = agent.getPosition();
            agents.put(position, agents.remove(old));
            agent.setPosition(position);
        }
    }

    /**
     * Affichage de la grille
     */
    public synchronized static void afficher() {
        // Generate border
        String trait = "    ";
        for (int i = 0; i < size; i++) {
            trait += "----";
        }

        // Display
        System.out.println(trait);
        for (int ligne = 1; ligne <= size; ligne++) {
            System.out.print(ligne + ": |");
            for (int colonne = 1; colonne <= size; colonne++) {
                Agent agent = get(ligne, colonne);
                if (agent != null) {
                    System.out.print("_" + agent.getSymbole() + "_|");
                } else {
                    System.out.print("___|");
                }
            }
            System.out.println("\n" + trait);
        }
    }
    
    /**
     * Affichage de la grille objetive
     */
    public static void afficherObjectifs() {
        // Generate border
        String trait = "    ";
        for (int i = 0; i < size; i++) {
            trait += "----";
        }

        // Display
        System.out.println(trait);
        for (int ligne = 1; ligne <= size; ligne++) {
            System.out.print(ligne + ": |");
            for (int colonne = 1; colonne <= size; colonne++) {
                Agent agent = objectifs.get(new Position(ligne, colonne));
                if (agent != null) {
                    System.out.print("_" + agent.getSymbole() + "_|");
                } else {
                    System.out.print("___|");
                }
            }
            System.out.println("\n" + trait);
        }
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        Grille.size = size;
    }

    public static Map<Position, Agent> getAgents() {
        return agents;
    }

    public static void setAgents(Map<Position, Agent> agents) {
        Grille.agents = agents;
    }
}
