import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;

public class AirTrafficControl implements drawable {
    private static ArrayList<Aircraft> aircraftsInAirportt;
    private static ArrayList<Node> airportNavigation;
    private Vector2 Location;
    
    AirTrafficControl(ArrayList<Aircraft> aircraftCount, ArrayList<Node> airportMap, Vector2 buildingLocation) {
        aircraftsInAirportt = aircraftCount;
        airportNavigation = airportMap;
        Location = buildingLocation;
    }

    public ArrayList<Node> calculateRoute(String NodeID, Node StartingNode) {
        ArrayList<Node> path = new ArrayList<>();
        if (NodeID != null && StartingNode != null) {
        path = findNode(NodeID, path, StartingNode);
        path = shortestPathNode(path);
        } else {
            throw new NullPointerException("Route calculations NodeID or startingNode is null or a incorrect data type!");
        }

        return path;
    }

    // find shortest path
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

        boolean reachedEnd = false; // detect if the waypoint has reached last
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i).equals(lastNode)) {
                reachedEnd = true;
            } else if (reachedEnd == true) {
                copy.remove(i); // removes any waypoints beyond the end waypoint
                i--;
            }
        }
        return copy;
    }

    // used to create a navigational arraylist of points on the airport
        public ArrayList<Node> findNode(String TargetedNode, ArrayList<Node> givenArray, Node startingNode) {
        if (givenArray.contains(startingNode)) { // ensure that a node can only be gone on once
            return null;
        }

        if (startingNode.NodeID != null && startingNode.NodeID.equals(TargetedNode)) {
            givenArray.add(startingNode);
            return givenArray;
        }

        if (startingNode.upperNode != null && !givenArray.contains(startingNode.upperNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.upperNode);
        }

        if (startingNode.bottomNode != null && !givenArray.contains(startingNode.bottomNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.bottomNode);
        }

        if (startingNode.leftNode != null && !givenArray.contains(startingNode.leftNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.leftNode);
        }

        if (startingNode.rightNode != null && !givenArray.contains(startingNode.rightNode)) {
            givenArray.add(startingNode);
            return findNode(TargetedNode, givenArray, startingNode.rightNode);
        }
        
        return null; // after checking that all other slots are null, meaning this branch isn't it
    }
    
    // for drawing elements of airtraffic control
    public void visualRepresentation(Graphics drawer, int width, int height) {
        Image catImage = new ImageIcon("Folder JUMPSCARE/cat.PNG").getImage();
        drawer.setColor(Color.GRAY);
        drawer.fillRect(Location.getXPos(), Location.getYPos(), width, height);
        drawer.setColor(Color.CYAN);
        drawer.fillRect(Location.getXPos(), Location.getYPos(), width, height);
    }

    // allows air traffic control to decide whether a plane can takeoff or not
    public void ClearAircraftForTakeOff(Aircraft selectedAircraft, Node otherAirportLocation) {
        if (selectedAircraft.canFly() == true) {
            selectedAircraft.setTarget(otherAirportLocation.getPosition());
        }
    }
}
