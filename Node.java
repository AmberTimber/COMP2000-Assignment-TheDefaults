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

    public ArrayList<Node> shortestPathNode(ArrayList<Node> givenArray) {
        ArrayList<Node> copy = new ArrayList<>(givenArray);
        Node lastNode = null; // used to remove anything beyond the last waypoint
        if (givenArray != null && !givenArray.isEmpty()) {
            lastNode = givenArray.get(givenArray.size() - 1);
            for (int i = 0; i < givenArray.size(); i++) { // starting node to check if future nodes are neighbors
                int furtherProgression = 0; // used to compare which node is further down
                for (int c = i; c < givenArray.size(); c++) {
                    if (copy.get(i).checkIfNeighboring(givenArray.get(c))) { // detects whether a neighboring node has a vector2
                        if (furtherProgression <= c && (i+1) < givenArray.size()) {
                            copy.set(i + 1, givenArray.get(c));
                            furtherProgression = c;
                        }
                    }
                    
                }
            }
        }

        boolean reachedEnd = false;
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i).equals(lastNode)) {
                reachedEnd = true;
            } else if (reachedEnd == true) {
                copy.remove(i);
                i--;
            }
        }
        return copy;
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


    /*public ArrayList<Vector2> findNode(String TargetedNode, ArrayList<Vector2> givenArray) {
        if (givenArray.contains(nodePosition)) { // ensure that a node can only be gone on once
            return null;
        }

        if (this.NodeID != null && this.NodeID.equals(TargetedNode)) {
            givenArray.add(nodePosition);
            return givenArray;
        }

        if (upperNode != null && !givenArray.contains(upperNode.getPosition())) {
            givenArray.add(nodePosition);
            return upperNode.findNode(TargetedNode, givenArray);
        }

        if (bottomNode != null && !givenArray.contains(bottomNode.getPosition())) {
            givenArray.add(nodePosition);
            return bottomNode.findNode(TargetedNode, givenArray);
        }

        if (leftNode != null && !givenArray.contains(leftNode.getPosition())) {
            givenArray.add(nodePosition);
            return leftNode.findNode(TargetedNode, givenArray);
        }

        if (rightNode != null && !givenArray.contains(rightNode.getPosition())) {
            /*ArrayList<Vector2> result = new ArrayList<>(currentPath);
            result.add(nodePosition);
            result = rightNode.findNode(TargetedNode, currentPath);
            if (result != null) {
                return result;
            }
            givenArray.add(nodePosition);
            return rightNode.findNode(TargetedNode, givenArray);
        }
        
        return null; // after checking that all other slots are null, meaning this branch isn't it
    }*/

        public ArrayList<Node> findNode(String TargetedNode, ArrayList<Node> givenArray) {
        if (givenArray.contains(this)) { // ensure that a node can only be gone on once
            return null;
        }

        if (this.NodeID != null && this.NodeID.equals(TargetedNode)) {
            givenArray.add(this);
            return givenArray;
        }

        if (upperNode != null && !givenArray.contains(upperNode)) {
            givenArray.add(this);
            return upperNode.findNode(TargetedNode, givenArray);
        }

        if (bottomNode != null && !givenArray.contains(bottomNode)) {
            givenArray.add(this);
            return bottomNode.findNode(TargetedNode, givenArray);
        }

        if (leftNode != null && !givenArray.contains(leftNode)) {
            givenArray.add(this);
            return leftNode.findNode(TargetedNode, givenArray);
        }

        if (rightNode != null && !givenArray.contains(rightNode)) {
            givenArray.add(this);
            return rightNode.findNode(TargetedNode, givenArray);
        }
        
        return null; // after checking that all other slots are null, meaning this branch isn't it
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
}
