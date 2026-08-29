public class Moveable {
    private float xPos = (int)(Math.random() * (100 - 1 + 1)) + 1;
    private float yPos = (int)(Math.random() * (100 - 1 + 1)) + 1;

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
}
