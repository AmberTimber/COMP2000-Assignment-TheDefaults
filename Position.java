
public interface  Position {
    int getXPos();
    int getYPos();

    void setXPos(int xPos);
    void setYPos(int yPos);


    default String returnVector2() {
        return (" This Xposition is: " + getXPos() + " . This Yposition is: " + getYPos());
    }
}
