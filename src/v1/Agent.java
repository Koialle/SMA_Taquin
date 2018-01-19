
package v1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import v1.exception.PositionUsedException;

/**
 *
 * @author DUBREUIL Mélanie
 * @author EOUZAN Ophélie
 */
public class Agent extends Thread {
    
    private Position position;
    private Position objectif;
    private final String symbole;
    private Queue<Message> messages = new LinkedList<>();
    
    public Agent(String symbole) {
        this.symbole = symbole;
    }

    @Override
    public void run() {
        try {
            while (!Grille.isSatisfied()) {
                // Check I have position
                if (this.getPosition() == null) {
                    throw new Exception("Missing position");
                }

                // check
                this.checkAllMessages();

                Position newPosition = this.calculateObjective();
                this.processMovement(newPosition);
            }
        } catch (Exception ex) {
            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void processMovement(Position position) {
        if (position != null) {
            try {
                System.out.printf("Agent %s: Trying to move to position (%d, %d)\n", symbole, position.getX(), position.getY());
                Grille.move(this, position);
                System.out.printf("Agent %s: Moved to position (%d, %d)\n", symbole, position.getX(), position.getY());
                
                Grille.afficher();
            } catch (Exception exception) {

                if (exception instanceof PositionUsedException) {
                    // envoi message
                    Agent agent = ((PositionUsedException) exception).getAgent();

                    Message message = new Message();
                    message.setEmetteur(this);
                    message.setDestinataire(agent);
                    message.setPosition(position);

                    agent.addMessage(message);

                    System.out.printf("Agent %s send message to agent %s for position (%d, %d)\n", symbole, agent.getSymbole(), position.getX(), position.getY());
                }
            }
        }
    }
    
    private synchronized void checkAllMessages() throws Exception {
        //@TODO handle blocking behaviour : when two agents want to exchange position
        Message message = null;
        while (messages.size() > 0) {
            message = messages.poll();
            if (message != null) {
                if (message.getPerformatif().equals(Message.PERFORMATIVE_REQUEST) && message.getAction().equals(Message.ACTION_MOVE)) {
                    Agent emetteur = message.getEmetteur();
                    System.out.printf("Agent %s (%d, %d) : Message reçu de %s pour la position (%d, %d)\n", symbole, position.getX(), position.getY(), emetteur.getSymbole(), message.getPosition().getX(), message.getPosition().getY());

                    if (this.position.equals(message.getPosition())) {
                        Position newPosition = Grille.move(this); // get position

                        if (newPosition != null) {
                            System.out.printf("Agent %s (%d, %d) : Tentative de déplacement vers (%d, %d) à la demande de %s\n", symbole, position.getX(), position.getY(), newPosition.getX(), newPosition.getY(), emetteur.getSymbole());
                        }

                        this.processMovement(newPosition);
                    } else {
                        System.out.printf("Agent %s (%d, %d) : Demande de déplacement par %s expirée\n", symbole, position.getX(), position.getY(), emetteur.getSymbole());
                    }
                }
            }
        }        
    }
    
    private Position calculateObjective() {
        if (this.position.equals(objectif)) {
            return null;
        }
            
        Position newPosition = new Position();

        // X
        if (this.position.getX() != objectif.getX()) {
            int newX = position.getX() > objectif.getX() ? position.getX() - 1 : position.getX() + 1;

            newPosition = new Position(newX, position.getY());
        } else if (this.position.getY() != objectif.getY()) { // Y
            int newY = position.getY() > objectif.getY() ? position.getY() - 1 : position.getY() + 1;

            newPosition = new Position(position.getX(), newY);
        }

        //Grille.move(this, newPosition); //@TODO handle exception here ?
        return newPosition;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getObjectif() {
        return objectif;
    }

    public void setObjectif(Position objectif) {
        this.objectif = objectif;
    }

    public String getSymbole() {
        return this.symbole;
    }

    private synchronized void addMessage(Message message) {
        this.messages.add(message);
    }
}
