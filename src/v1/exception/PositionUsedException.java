
package v1.exception;

import v1.Agent;

/**
 *
 * @author Mélanie
 * @author Ophélie
 */
public class PositionUsedException extends Exception {

    private Agent agent = null;

    public PositionUsedException() {
        super("Position already in use");
    }
    
    public PositionUsedException(Agent agent) {
        super("Position already in use");
        this.agent = agent;
    }

    public PositionUsedException(String message, Agent agent) {
        super(message);
        this.agent = agent;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
