
package v1;

import java.util.Random;

/**
 *
 * @author Mélanie
 * @author Ophélie
 */
public class Position {
    
    private Random rand;
    private int x;
    private int y;

    public Position() {
    }

    public Position(int size) {
        this.rand = new Random();
        this.x = rand.nextInt(size) + 1;
        this.y = rand.nextInt(size) + 1;
    }

    public Position(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object object)
    {
        if (object instanceof Position) {
            if (this.x == ((Position) object).getX() && this.y == ((Position) object).getY()) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        return hash;
    }
}
