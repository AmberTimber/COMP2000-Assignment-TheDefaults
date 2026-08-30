import java.lang.annotation.Target;

public class Moveable {
    private int xPos = (int)(Math.random() * (900 - 1 + 1)) + 1;
    private int yPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
    private float minDist = 0.5f;
    private Vector2 target;
    private boolean reachTarget = false;

    public void SetMove (int xpos, int ypos) {
        setxPos(xpos);
        setyPos(ypos);
    }

    // getters
    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public boolean getReachedTarget() {
        return reachTarget;
    }

    public void setReachedTarget(boolean value) {
        reachTarget = value;
    }

    // setters
    public void setxPos(int xpos) {
        this.xPos = xpos;
    }

    public void setyPos(int ypos) {
        this.yPos = ypos;
    }

    public void setTarget(Vector2 targetPosition) {
        target = targetPosition;
    }

    public void moveTowards(int speed) {
        moveTowardsTargetXAxis(speed);
        moveTowardsTargetYAxis(speed);
    }

    public void moveTowardsTargetXAxis(int speed) {
        if (target.getXPos() > this.xPos) {
            xPos += speed;
        }
        if (target.getXPos() < this.xPos) {
            xPos += -speed;
        }
    }

    public void moveTowardsTargetYAxis(int speed) {
        if (target.getyPos() > this.yPos) {
            yPos += speed;
        }
        if (target.getyPos() < this.yPos) {
            yPos += -speed;
        }
    }

    public boolean checkIfReachTarget() {
        if (this.xPos == target.getXPos() && this.yPos == target.getyPos()) {
            return true;
        }
        return false;
    }

    public void changeTarget() {
        int newXpos = (int)(Math.random() * (1000 - 1 + 1)) + 1;
        int newYpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
        target = new Vector2(newXpos, newYpos);
        System.out.println("New pos is: " + newXpos + " x value, " + newYpos + " y value.");
    }
}
