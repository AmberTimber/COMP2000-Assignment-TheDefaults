import java.util.ArrayList;
import java.util.Objects;

public class Node implements Position {
    public String NodeID;
    Node upperNode;
    Node bottomNode;
    Node leftNode;
    public Node rightNode;
    Vector2 nodePosition;
    boolean isOccupied = false; // determines if a node is obstructed or not
    String locatedIn;
    
    // maker
    public Node (Node upperNode, Node bottomNode, Node leftNode, Node rightNode, Vector2 nodePosition, String NodeID, String Location) {
        setUpperNode(upperNode);
        setBottomNode(bottomNode);
        setLeftNode(leftNode);
        setRightNode(rightNode);
        this.nodePosition = nodePosition;
        this.NodeID = NodeID;
        locatedIn = Location;
    }

    public Vector2 getPosition () {
        return nodePosition;
    }

    public String getNodeTileRepresentation() {
        return locatedIn;
    }

    public boolean checkIfNeighboring(Node targetedVector) { // detects whether a neighboring node has a targetedVector
        if (targetedVector == null) {
            return false;
        }
        
        if (upperNode != null && upperNode.equals(targetedVector)) {
            return true;
        } else if (bottomNode != null && bottomNode == targetedVector) {
            return true;
        } else if (leftNode != null && leftNode == targetedVector) {
            return true;
        } else if (rightNode != null && rightNode == targetedVector) {
            return true;
        }
        return false;
    }

    // setters
    public void setUpperNode(Node newNode) {
        this.upperNode = newNode;
    }

    public void setBottomNode(Node newNode) {
        this.bottomNode = newNode;
    }

    public void setLeftNode(Node newNode) {
        this.leftNode = newNode;
    }

    public void setRightNode(Node newNode) {
        this.rightNode = newNode;
    }
    
    public void setXPos(int xpos) {
        nodePosition.xPos = xpos;
    }

    public void setYPos(int ypos) {
        nodePosition.yPos = ypos;
    }

    public void setOccupied(boolean value) {
        isOccupied = value;
    }

    // getters
    public Node getUpperNode() {
        return upperNode;
    }

    public Node getBottomNode() {
        return bottomNode;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public int getXPos() {
        return nodePosition.xPos;
    }

    public int getYPos() {
        return nodePosition.yPos;
    }

    public boolean getOccupied() {
        return isOccupied;
    }
}