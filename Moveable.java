
import java.util.ArrayList;
import java.util.Vector;

public class Moveable extends Vector2 {
    private float minDist = 0.5f;
    private Vector2 target;
    private boolean reachTarget = false;
    private ArrayList<Node> flightPath = new ArrayList<>();

    public Moveable(){}

    public Moveable(Vector2 position) {
        setXPos(position.xPos);
        setYPos(position.yPos);
    } 

    public Moveable (int xpos, int ypos) {
        setXPos(xpos);
        setYPos(ypos);
    }

    public void moveTowards(int speed) { // moves towards target at a speed
        moveTowardsTargetXAxis(speed);
        moveTowardsTargetYAxis(speed);
        checkIfReachTarget();
    }

    // getters
    public boolean getReachedTarget() {
        return reachTarget;
    }

    public Vector2 getTarget() {
        return target;
    }

    public ArrayList<Node> getFlightPath() {
        return flightPath;
    }

    // setters
    public void setReachedTarget(boolean value) {
        reachTarget = value;
    }

    public void setTarget(Vector2 targetPosition) {
        target = targetPosition;
    }

    public void setFlightPath(ArrayList<Node> givenPath) { // set path for things to move to
        flightPath = givenPath;
    }

    // used for moving the object to a position
    public void moveTowardsTargetXAxis(int speed) {
        if (target.getXPos() > this.getXPos()) {
            this.setXPos(this.getXPos() + speed);
        }
        if (target.getXPos() < this.getXPos()) {
            this.setXPos(this.getXPos() + -speed);
        }
    }

    public void moveTowardsTargetYAxis(int speed) {
        if (target.getYPos() > this.getYPos()) {
            this.setYPos(this.getYPos() + speed);
        }
        if (target.getYPos() < this.getYPos()) {
            this.setYPos(this.getYPos() + -speed);
        }
    }

    public void checkIfReachTarget() {
        if (checkReachXAxis() == true && checkReachYAxis() == true || this.getXPos() == target.getXPos() && this.getYPos() == target.getYPos()) {
            reachTarget = true;
            System.out.println("Reached pos!");
            //changeTarget();
        }
    }

    private boolean checkReachXAxis () {
        if (target.getXPos() < this.getXPos()) { // if target x is less than current position
            if ((this.getXPos() - target.getXPos()) <= minDist) {
                return true;
            }
        } else if (target.getXPos() > this.getXPos()) { // if target x is more than current position
            if ((target.getXPos() - this.getXPos()) <= minDist) {
                return true;
            }
        }
        return false;
    }


    private boolean checkReachYAxis () {
        if (target.getYPos() < this.getYPos()) { // if target x is less than current position
            if ((this.getYPos() - target.getYPos()) <= minDist) {
                return true;
            }
        } else if (target.getYPos() > this.getYPos()) { // if target x is more than current position
            if ((target.getYPos() - this.getYPos()) <= minDist) {
                return true;
            }
        }
        return false;
    }

    public void changeTarget() {
        int newXpos = (int)(Math.random() * (1000 - 1 + 1)) + 1;
        int newYpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
        target = new Vector2(newXpos, newYpos);
        //System.out.println("New pos is: " + newXpos + " x value, " + newYpos + " y value.");
        reachTarget = false;
    }
}
