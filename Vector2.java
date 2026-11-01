import java.util.Objects;

public class Vector2 implements Position {
    private int xPos;
    private int yPos;

    public Vector2() {} // goes with just random position

    public Vector2 (int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Vector2 getPosition() {
        return new Vector2(xPos, yPos);
    }

    public void setVector2(int newxPos, int newyPos) {
        setXPos(newxPos);
        setYPos(newyPos);
    }

    public void setVector2(Vector2 newPos) {
        setXPos(newPos.getXPos());
        setYPos(newPos.getYPos());
    }

    public void setPosition(Vector2 newPos) {
        xPos = newPos.getXPos();
        yPos = newPos.getYPos();
    }

    public boolean compareVectors(Vector2 otherVector) {
        if (getXPos() == otherVector.getXPos() && getYPos() == otherVector.getYPos()) {
            return true;
        }
        return false;
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

    @Override
    public boolean equals(Object otherObject) {
        // If the object is compared with itself then return true  
        if (this == otherObject) {
            return true;
        }
        
        // Check if otherObject is an instance of vector 2
        if (otherObject == null || this.getClass() != otherObject.getClass()) {
            return false;
        }
        
        // Cast otherObject to Vector2 so that we can compare data members 
        Vector2 otherVector2 = (Vector2) otherObject;
        
        // Compare the actual field values
        return xPos == otherVector2.xPos && yPos == otherVector2.yPos;
    }

    // Override hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(xPos, yPos);
    }
}
