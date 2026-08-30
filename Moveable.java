public class Moveable {
    private float xPos = (int)(Math.random() * (100 - 1 + 1)) + 1;
    private float yPos = (int)(Math.random() * (100 - 1 + 1)) + 1;
    private float minDist = 1f;
    private Moveable target;
    private boolean reachTarget = false;

    public void SetMove (float xpos, float ypos) {
        setxPos(xpos);
        setyPos(ypos);
    }

    // getters
    public float getxPos() {
        return xPos;
    }

    public float getyPos() {
        return yPos;
    }

    // setters
    public void setxPos(float xpos) {
        this.xPos = xpos;
    }

    public void setyPos(float ypos) {
        this.yPos = ypos;
    }

    public void setTarget(Moveable targetPosition) {
        target = targetPosition;
    }

    public void moveTowardsTargetXAxis(int speed) {
        if (target.getxPos() > this.xPos) {
            xPos += speed;
        }
        if (target.getxPos() < this.xPos) {
            xPos -= speed;
        }
    }

    public void moveTowardsTargetYAxis(int speed) {
        if (target.getxPos() > this.xPos) {
            yPos += speed;
        }
        if (target.getxPos() < this.xPos) {
            yPos -= speed;
        }
    }

    public void checkIfReachTarget() {
        if (this.xPos - target.getxPos() < minDist) {
            reachTarget = true;
            this.xPos = target.getxPos();
        } else {
            reachTarget = false;
        }
        if (this.yPos - target.getyPos() < minDist) {
            reachTarget = true;
            this.yPos = target.getxPos();
        } else {
            reachTarget = false;
        }
    }
}
