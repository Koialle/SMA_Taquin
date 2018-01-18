
package v1;

/**
 *
 * @author Mélanie
 * @author Ophélie
 */
public class Message {
    
    public static final String ACTION_MOVE = "move";
    public static final String PERFORMATIVE_REQUEST = "request";
    
    private Position position;
    private Agent emetteur;
    private Agent destinataire;
    
    /**
     * Inform, request, ...
     */
    private String performatif;
    
    /**
     * Move
     */
    private String action;

    public Message() {
        this.performatif = PERFORMATIVE_REQUEST;
        this.action = ACTION_MOVE;
    }
    
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getPerformatif() {
        return performatif;
    }

    public void setPerformatif(String performatif) {
        this.performatif = performatif;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Agent getEmetteur() {
        return emetteur;
    }

    public void setEmetteur(Agent emetteur) {
        this.emetteur = emetteur;
    }

    public Agent getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(Agent destinataire) {
        this.destinataire = destinataire;
    }
}
