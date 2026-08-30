public class Vector2 {
    private int xPos;
    private int yPos;

    public Vector2 (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos () {
        return xPos;
    }

    public int getyPos () {
        return yPos;
    }

    public void randomPos() {
        xPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
        yPos = (int)(Math.random() * (800 - 1 + 1)) + 1;
    }
}
