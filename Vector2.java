public class Vector2 implements Position {
    public int xPos = (int)(Math.random() * (900 - 1 + 1)) + 1;
    public int yPos = (int)(Math.random() * (900 - 1 + 1)) + 1;

    public Vector2() {} // goes with just random position

    public Vector2 (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    // getters
    @Override
    public int getXPos () {
        return xPos;
    }

    @Override
    public int getYPos () {
        return yPos;
    }

    // setters
    @Override
    public void setXPos(int xpos) {
        this.xPos = xpos;
    }

    @Override
    public void setYPos(int ypos) {
        this.yPos = ypos;
    }

    public void randomPos() {
        xPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
        yPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
    }
}
