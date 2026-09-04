import java.util.ArrayList;
import java.util.Objects;

public class Node {
    public String NodeID;
    Node upperNode;
    Node bottomNode;
    Node leftNode;
    public Node rightNode;
    Vector2 nodePosition;
    

    public Node (Node upperNode, Node bottomNode, Node leftNode, Node rightNode, Vector2 nodePosition, String NodeID) {
        setUpperNode(upperNode);
        setBottomNode(bottomNode);
        setLeftNode(leftNode);
        setRightNode(rightNode);
        this.nodePosition = nodePosition;
        this.NodeID = NodeID;
    }

    public Vector2 getPosition () {
        return nodePosition;
    }


    public ArrayList<Vector2> findNode(String TargetedNode, ArrayList<Vector2> givenArray) {
        ArrayList<Vector2> currentPath = new ArrayList<>(givenArray);
        currentPath.add(nodePosition);

        if (this.NodeID != null && this.NodeID.equals(TargetedNode)) {
            givenArray.add(nodePosition);
            return givenArray;
        }

        if (upperNode != null && !givenArray.contains(upperNode.getPosition())) {
            ArrayList<Vector2> result = new ArrayList<>(givenArray);
            result.add(nodePosition);
            result = upperNode.findNode(TargetedNode, result);
            if (result != null) {
                return result;
            }
        }

        if (bottomNode != null && !givenArray.contains(bottomNode.getPosition())) {
            ArrayList<Vector2> result = new ArrayList<>(givenArray);
            result.add(nodePosition);
            result = bottomNode.findNode(TargetedNode, result);
            if (result != null) {
                return result;
            }
            //givenArray.add(nodePosition);
            //return bottomNode.findNode(TargetedNode, givenArray);
        }

        if (leftNode != null && !givenArray.contains(leftNode.getPosition())) {
            ArrayList<Vector2> result = new ArrayList<>(givenArray);
            result.add(nodePosition);
            result = leftNode.findNode(TargetedNode, result);
            if (result != null) {
                return result;
            }
            //givenArray.add(nodePosition);
            //return leftNode.findNode(TargetedNode, givenArray);
        }

        if (rightNode != null && !givenArray.contains(rightNode.getPosition())) {
            ArrayList<Vector2> result = new ArrayList<>(givenArray);
            result.add(nodePosition);
            result = rightNode.findNode(TargetedNode, result);
            if (result != null) {
                return result;
            }
            //givenArray.add(nodePosition);
            //return rightNode.findNode(TargetedNode, givenArray);
        }
        
        return null; // after checking that all other slots are null,
    }


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
}
