import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class AirTrafficControl implements drawable, Position {
    private static ArrayList<Aircraft> aircraftsInAirport;
    private static ArrayList<Node> airportNavigation;
    private Vector2 Location;
    private boolean OccupiedAirfield = false;
    private static Aircraft flyingAircraft = null;

    
    AirTrafficControl(ArrayList<Aircraft> aircraftCount, ArrayList<Node> airportMap, Vector2 buildingLocation) {
        aircraftsInAirport = aircraftCount;
        airportNavigation = airportMap;
        Location = buildingLocation;
    }

    public int getXPos() {
        return Location.xPos;
    }

    public int getYPos() {
        return Location.yPos;
    }

    public void setXPos(int xPos) {
        Location.xPos = xPos;
    }
    
    public void setYPos(int yPos) {

    }

    public Vector2 getPosition() {
        return Location;
    }

    public boolean getOccupiedAirfield() {
        return OccupiedAirfield;
    }

    // creating a flight path for planes
    public ArrayList<Node> calculateRoute(String NodeID, Node StartingNode) {
        ArrayList<Node> path = new ArrayList<>();
        if (NodeID != null && StartingNode != null) {
            try {
                StartingNode.setOccupied(false);
                path = findNode(NodeID, path, StartingNode);
                StartingNode.setOccupied(true);
                path = shortestPathNode(path);
            } catch (NullPointerException e) {
                System.out.println("NULL ERROR IN PATH: " + e);
                return null;
            }
        } else {
            throw new NullPointerException("Route calculations NodeID or startingNode is null or a incorrect data type!");
        }

        return path;
    }

    // find shortest path
    private ArrayList<Node> shortestPathNode(ArrayList<Node> givenArray) {
        ArrayList<Node> copy = new ArrayList<>(givenArray);
        Node lastNode = null; // used to remove anything beyond the last waypoint
        if (givenArray != null && !givenArray.isEmpty() && copy != null) {
            lastNode = givenArray.get(givenArray.size() - 1);
            for (int i = 0; i < givenArray.size(); i++) { // starting node to check if future nodes are neighbors
                int furtherProgression = 0; // used to compare which node is further down
                for (int c = i; c < givenArray.size(); c++) {
                    if (copy.get(i).checkIfNeighboring(givenArray.get(c))) { // detects whether a neighboring node has a vector2
                        //  && copy.get(i).getOccupied() == false || copy.get(i).checkIfNeighboring(givenArray.get(c)) && givenArray.get(i) == givenArray.get(givenArray.size()-1) || copy.get(i).checkIfNeighboring(givenArray.get(c)) && givenArray.get(i).getNodeTileRepresentation().equalsIgnoreCase("RUNWAY")
                        if (furtherProgression <= c && (i+1) < givenArray.size()) {
                            copy.set(i + 1, givenArray.get(c));
                            furtherProgression = c;
                        }
                    }
                    
                }
            }
         } else {
                throw new NullPointerException("The path is null, something broke in the path!");
        }

        boolean reachedEnd = false; // detect if the waypoint has reached last
        for (int i = 0; i < copy.size(); i++) {
            if (copy != null && copy.get(i).equals(lastNode)) {
                reachedEnd = true;
            } else if (reachedEnd == true) {
                copy.remove(i); // removes any waypoints beyond the end waypoint
                i--;
            }
        }
        return copy;
    }

    // used to create a navigational arraylist of points on the airport
        private ArrayList<Node> findNode(String TargetedNode, ArrayList<Node> givenArray, Node startingNode) {
        if (startingNode == null || givenArray.contains(startingNode) || startingNode.isOccupied && !startingNode.getNodeTileRepresentation().equalsIgnoreCase("RUNWAY")) { // ensure that a node can only be gone on once
            return null;
        }

        givenArray.add(startingNode); // adds

        // if correct node
        if (startingNode.getNodeID() != null && startingNode.getNodeID().equals(TargetedNode)) {
            return givenArray;
        }

        ArrayList<Node> finalPath; // used to hold all pathways and check if reached
        
        finalPath = findNode(TargetedNode, givenArray, startingNode.upperNode);
        if (finalPath != null) {
            return finalPath;
        }

        finalPath = findNode(TargetedNode, givenArray, startingNode.bottomNode);
        if (finalPath != null) {
            return finalPath;
        }

        finalPath = findNode(TargetedNode, givenArray, startingNode.leftNode);
        if (finalPath != null) {
            return finalPath;
        }

        finalPath = findNode(TargetedNode, givenArray, startingNode.rightNode);
        if (finalPath != null) {
            return finalPath;
        }

       givenArray.remove(givenArray.size()-1);
        System.out.println("Returned null in path!");
        return null; // after checking that all other slots are null, meaning this branch isn't it
    }
    
    // for drawing elements of airtraffic control
    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawer.setColor(Color.GRAY);
        drawer.fillRect(Location.getXPos()-width/2, Location.getYPos()-height/2, width, height);
        drawer.setColor(Color.CYAN);
        drawer.fillRect(Location.getXPos(), Location.getYPos(), width, height);
    }

    // allows air traffic control to decide whether a plane can takeoff or not
    public void ClearAircraftForTakeOff(Aircraft selectedAircraft, ArrayList<Node> flightOutside, ArrayList<Node> airfieldRef) {
        if (airfieldRef != null && !airfieldRef.isEmpty() && selectedAircraft != null && flightOutside != null && !flightOutside.isEmpty()) {
            if (selectedAircraft.canFly() == true && selectedAircraft.getChosenToFly()) {
                System.out.println("Go for takeoff!!!");
                ResetAircraft(flyingAircraft);
                OccupiedAirfield = true;
                AirfieldNodeChanger(airfieldRef);
                flyingAircraft = selectedAircraft;
                flyingAircraft.setFlying(true);
                flyingAircraft.setFlightPath(flightOutside);
            }
        }
    }

    // changes all airfield nodes to be a value
    public void AirfieldNodeChanger (ArrayList<Node> airfieldRef) {
        if (airfieldRef != null && !airfieldRef.isEmpty())
        for (int i = 0; i < airfieldRef.size(); i++) {
            airfieldRef.get(i).setOccupied(OccupiedAirfield);
        }
    }

    // if aircraft flew off runway, free up runway
    public void checkIfAirfieldIsFree(int width, ArrayList<Node> airfieldRef) {
        if (flyingAircraft != null && flyingAircraft.getXPos() < 0 || flyingAircraft != null && !flyingAircraft.getCurrentNode().getNodeTileRepresentation().equalsIgnoreCase("RUNWAY")) {
            OccupiedAirfield = false;
            AirfieldNodeChanger(airfieldRef);
            flyingAircraft.setSelected(false);
            flyingAircraft = null;
        }
    }

    // detects if a aircraft is onfield
    public void checkIfAAircraftOnAirfield(ArrayList<Node> airfieldRef, Aircraft otherAircraft) {
        if (aircraftsInAirport != null && !aircraftsInAirport.isEmpty() && airfieldRef != null && !airfieldRef.isEmpty() && otherAircraft != null && flyingAircraft == null) {
            for (int i = 0; i < airfieldRef.size(); i++) {
                if (otherAircraft.getCurrentNode() == airfieldRef.get(i)) {
                    OccupiedAirfield = true;
                    AirfieldNodeChanger(airfieldRef);
                    flyingAircraft = otherAircraft;
                    flyingAircraft.setSelected(true);
                    i = airfieldRef.size();
                }
            }
        }
    }

    // check if clear for landing
    public void clearForLanding(Aircraft selectedAircraft, ArrayList<Node> airfieldRef, ArrayList<Node> airportNav) {
        if (airfieldRef != null && !airfieldRef.isEmpty() && selectedAircraft != null && airportNav != null && !airportNav.isEmpty()) {
            if (selectedAircraft.getFlying() && selectedAircraft.isAtLastNode()) {
                if (OccupiedAirfield == false && flyingAircraft == null) {
                    selectedAircraft.getCurrentNode().setOccupied(false);
                    flyingAircraft = selectedAircraft;
                    ResetAircraft(flyingAircraft);
                    flyingAircraft.setSelected(true);
                    flyingAircraft.setFlying(false);
                    flyingAircraft.setFlightPath(airportNav);
                    flyingAircraft.setStatus("GROUNDED");
                    OccupiedAirfield = true;
                    AirfieldNodeChanger(airfieldRef);
                    System.out.println("attempting to land!");
                }
            }
        }
    }

    // resets flight path of aircraft
    private void ResetAircraft (Aircraft selectedAircraft) {
        if (selectedAircraft != null) {
            selectedAircraft.resetIndex();
            selectedAircraft.setReachedTarget(false);
        }
    }

    // used for getting a random location on runway and gate
    public String getRandomNodeID(ArrayList<Node> nodeSet) {
        if (nodeSet != null && !nodeSet.isEmpty()) {
            int numberSelected = (int)(Math.random() * nodeSet.size());
            Node selectedNode = nodeSet.get(numberSelected);
            return selectedNode.getNodeID();
        } else {
            System.out.println("Cannot get a random node ID. Array is null or is empty");
            return null;
        }
    }
}

