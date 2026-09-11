
import java.util.ArrayList;

public class Moveable extends Vector2  {
    private float minDist = 0.5f;
    private Vector2 target;
    private boolean reachTarget = false;
    private ArrayList<Node> flightPath = new ArrayList<>();
    private int NavigationIndex = 0;
    private boolean blocking = false;
    private Node currentNode;
    private boolean selected = false;
    private boolean atGate = false;

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

    public boolean isBlocked() {
        return blocking;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public Boolean getChosenToFly() {
        return selected;
    }

    public Boolean isAtGate() {
        return atGate;
    }

    // setters
    public void setReachedTarget(boolean value) {
        reachTarget = value;
    }

    public void setTarget(Vector2 targetPosition) {
        target = targetPosition;
    }

    public void setFlightPath(ArrayList<Node> givenPath) { // set path for things to move to
        if (givenPath != null && !givenPath.isEmpty()) {
            if (flightPath != null && !flightPath.isEmpty()) {
                currentNode.setOccupied(false);
                for (int i = 0; i < flightPath.size(); i++) {
                    flightPath.get(i).setOccupied(false);
                }
                reachTarget = false;
            }
        resetIndex();
        currentNode = givenPath.get(NavigationIndex);
        flightPath = givenPath;
        setTarget(flightPath.get(NavigationIndex).getPosition());
        } else {
            System.out.println("Null array or empty array given as flight path!!!!");
        }
    }

    public void setBlocked(boolean value) {
        blocking = value;
    }

    public void setSelected(boolean value) {
        selected = value;
    }

    public void resetIndex() {
        NavigationIndex = 0;
    }

    public void setDocked(boolean value) {
        atGate = value;
    }

    // used for moving the object to a position
    private void moveTowardsTargetXAxis(int speed) {
        if (target.getXPos() > this.getXPos()) {
            this.setXPos(this.getXPos() + speed);
        }
        if (target.getXPos() < this.getXPos()) {
            this.setXPos(this.getXPos() + -speed);
        }
    }

    private void moveTowardsTargetYAxis(int speed) {
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
        reachTarget = false;
    }

    public void MoveThroughFlightPath(int speed) { // moves through given node movement
        if (flightPath != null && !flightPath.isEmpty()) {
            if (!currentNode.getNodeTileRepresentation().equalsIgnoreCase("RUNWAY")) {
                selected = false;
            }
            if (flightPath.size() > NavigationIndex) {
                moveTowards(speed);
                checkIfReachTarget();
                flightPath.get(NavigationIndex).setOccupied(true);
    
                if (getReachedTarget() == true && flightPath.size() > NavigationIndex + 1 && flightPath.get(NavigationIndex +1).getOccupied() == false) {
                    flightPath.get(NavigationIndex).setOccupied(false); // frees up node for other aircrafts to go to
                    NavigationIndex++;
                    setTarget(flightPath.get(NavigationIndex).getPosition());
                    currentNode = flightPath.get(NavigationIndex);
                    setReachedTarget(false);
                    flightPath.get(NavigationIndex).setOccupied(true); // ensure no other aircrafts can go to the node
                    System.out.println("New target set!");
                } else if (getReachedTarget() == true && flightPath.size() > NavigationIndex + 1 && flightPath.get(NavigationIndex +1).getOccupied() == true && selected == false) {
                    CheckIfNextPathIsBlocked();
                } // if the aircraft is selected to go on airfield
                else if (getReachedTarget() == true && flightPath.size() > NavigationIndex + 1 && selected == true && flightPath.get(NavigationIndex).getNodeTileRepresentation().equalsIgnoreCase("RUNWAY")) {
                    flightPath.get(NavigationIndex).setOccupied(false);
                    NavigationIndex++;
                    setTarget(flightPath.get(NavigationIndex).getPosition());
                    currentNode = flightPath.get(NavigationIndex);
                    setReachedTarget(false);
                    flightPath.get(NavigationIndex).setOccupied(true); // ensure no other aircrafts can go to the node
                    System.out.println("Going to airfield");
                }
            }
        }
    }

    public void CheckIfNextPathIsBlocked() {
        if (flightPath != null && !flightPath.isEmpty() && getReachedTarget() == true && flightPath.size()-1 > NavigationIndex + 1 && flightPath.get(NavigationIndex +1).getOccupied() == true) {
                blocking = true;
                System.out.println("I am being blocked!!!");
            } else {
            blocking = false;
        }
    }

    // if reached end of nav path
    public boolean checkIfEndOfPath() {
        if (flightPath != null && !flightPath.isEmpty() && flightPath.size() <= NavigationIndex + 1 || flightPath != null && !flightPath.isEmpty() && flightPath.get(flightPath.size()-1).getPosition().compareVectors(flightPath.get(NavigationIndex).getPosition())) {
            return true;
        } else {
            return false;
        }
    }

    // returns last node in flight path
    public Node getLastNodeInFlightPath() {
        return flightPath.get(flightPath.size()-1);
    }

    public boolean isAtLastNode() { // detects whether at last node
        if (NavigationIndex >= flightPath.size()-1 || flightPath.get(flightPath.size()-1).CompareNodes(currentNode)) {
            return true;
        } else {
            return false;
        }
    }

    // used to reverse aircraft
    public void reverseAircraft() {
        if (NavigationIndex > 0 && !flightPath.get(NavigationIndex-1).isOccupied) {
            currentNode.setOccupied(false);
            NavigationIndex--;
            setBlocked(false);
        } else {
            System.out.println("Cannot reverse, path is already at start!");
        }
    }
}
