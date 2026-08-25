public class Moveable {
    private float xPos;
    private float yPos;

    public void SetMove (float xpos, float ypos) {
        this.xPos = xpos;
        this.yPos = ypos;
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
