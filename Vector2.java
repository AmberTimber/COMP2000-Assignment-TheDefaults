public class Vector2 {
    private int xPos = (int)(Math.random() * (900 - 1 + 1)) + 1;
    private int yPos = (int)(Math.random() * (900 - 1 + 1)) + 1;

    public Vector2() {} // goes with just random position

    public Vector2 (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // getters
    public int getXPos () {
        return xPos;
    }

    public int getYPos () {
        return yPos;
    }

    // setters
    public void setXPos(int xpos) {
        this.xPos = xpos;
    }

    public void setYPos(int ypos) {
        this.yPos = ypos;
    }

    public void randomPos() {
        xPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
        yPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
    }
}
