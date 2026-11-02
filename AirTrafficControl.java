import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AirTrafficControl implements drawable, Position {
    private static ArrayList<Aircraft> aircraftsInAirport;
    private static ArrayList<Node> airportNavigation;
    private static Vector2 Location;
    private boolean OccupiedAirfield = false;
    private static Aircraft flyingAircraft = null;
    private static final runwayNotifier notifier = new runwayNotifier();; // notifies planes of free runway
    private static Node exitNode;
    private static ArrayList<Node> runway;
    private static ArrayList<AirwayGate> gateList;
    private static Node entryNode;

    
    AirTrafficControl(ArrayList<Aircraft> aircraftCount, ArrayList<Node> airportMap, Vector2 buildingLocation, ArrayList<Node> Runway, ArrayList<AirwayGate> gates) {
        aircraftsInAirport = aircraftCount;
        airportNavigation = airportMap;
        Location = buildingLocation;
        runway = Runway;
        gateList = gates;
    }

    public runwayNotifier GetNotifier() {
        return notifier;
    }

    public void setExitNode(Node ExitNode) {
        exitNode = ExitNode;
    }

    public void setEntryNode(Node EntryNode) {
        entryNode = EntryNode;
    }

   public Vector2 getPosition() {
    return Location;
   }
    
    public void setPosition(int xPos, int yPos) {
        Location.setVector2(xPos, yPos);
    }

    public void setPosition(Vector2 newPos) {
        Location.setVector2(newPos);
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
        if (startingNode == null || givenArray.contains(startingNode) || startingNode.isOccupied && startingNode.getNodeTileRepresentation() != Node.locationTypes.AIRFIELD) { // ensure that a node can only be gone on once
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

    public void ClearAircraftForTakeOff(Aircraft selectedAircraft) {
        if (runway != null && !runway.isEmpty() && selectedAircraft != null) {
            if (selectedAircraft.canFly() == true && selectedAircraft.getChosenToFly()) {
                System.out.println("Go for takeoff!!!");
                ResetAircraft(flyingAircraft);
                OccupiedAirfield = true;
                AirfieldNodeChanger(runway);
                flyingAircraft = selectedAircraft;
                flyingAircraft.setFlying(true);
                ArrayList<Node> flightOutside = airportNavigation.stream()
                .filter(node -> node.getNodeTileRepresentation() == Node.locationTypes.OUTSIDE)
                .collect(Collectors.toCollection(ArrayList::new));
                flyingAircraft.setFlightPath(flightOutside);
            }
        }
    }

    // changes all airfield nodes to be a value
    public void AirfieldNodeChanger (ArrayList<Node> airfieldRef) {
        if (airfieldRef != null && !airfieldRef.isEmpty())
        airfieldRef.forEach(node -> node.setOccupied(OccupiedAirfield));

    }

    // if aircraft flew off runway, free up runway
    public void checkIfAirfieldIsFree(int flyoffDist, ArrayList<Node> airfieldRef) {
        if (flyingAircraft != null && flyingAircraft.getXPos() < 0
         || flyingAircraft != null && flyingAircraft.getCurrentNode().getNodeTileRepresentation() != Node.locationTypes.AIRFIELD && flyingAircraft.getCurrentNode() != exitNode) {
            OccupiedAirfield = false;
            AirfieldNodeChanger(airfieldRef);
            flyingAircraft.setSelected(false);
            flyingAircraft = null;
            notifier.Notify(true);
        }
    }

    // detects if a aircraft is onfield
    
public void checkIfAAircraftOnAirfield(
        ArrayList<Node> airfieldRef, Aircraft otherAircraft) {

    if (aircraftsInAirport != null
            && !aircraftsInAirport.isEmpty()
            && airfieldRef != null
            && !airfieldRef.isEmpty()
            && otherAircraft != null
            && flyingAircraft == null) {

        airfieldRef.stream()
            .filter(node -> otherAircraft.getCurrentNode() == node)
            .findFirst()
            .ifPresent(node -> {
                OccupiedAirfield = true;
                AirfieldNodeChanger(airfieldRef);
                flyingAircraft = otherAircraft;
                flyingAircraft.setSelected(true);
                notifier.Notify(false);
            });
    }
}


    // check if clear for landing
    public void clearForLanding(Aircraft selectedAircraft) {
        if (selectedAircraft != null) {
             Optional<AirwayGate> selectedGate = gateList.stream().filter(gate -> gate.getAssigned() == false).findFirst();
            if (selectedAircraft.getFlying() && selectedAircraft.isAtLastNode()) {
                if (OccupiedAirfield == false && flyingAircraft == null && selectedGate != null) {
                    selectedAircraft.getCurrentNode().setOccupied(false);
                    flyingAircraft = selectedAircraft;
                    ResetAircraft(flyingAircraft);
                    flyingAircraft.setSelected(true);
                    flyingAircraft.setFlying(false);
                    // creates stream from gates available and instantly assigns first one that is free
                    Node airfieldNode = runway.get(0);
                    AirwayGate gateref = selectedGate.orElse(null);
                    ArrayList<Node> flightPath = calculateRoute(gateref.getGateNode().getNodeID(), airfieldNode);
                    flightPath.add(0, entryNode);
                    flyingAircraft.setFlightPath(flightPath);
                    flyingAircraft.setGate(gateref);
                    gateref.setAssigned(true);
                    OccupiedAirfield = true;
                    AirfieldNodeChanger(runway);
                    System.out.println("attempting to land!");
                    flyingAircraft.setState(flyingAircraft.getGroundedState());
                    notifier.unSubscribe(flyingAircraft);
                }
            } else {
                System.out.println("No gates are free or are found!");
            }
        }
    }

    public void SelectAnAircraft() {
        Aircraft selectedPlane = notifier.getRandomAircraft();
        if (selectedPlane != null && flyingAircraft == null && OccupiedAirfield == false) {
            // gets a random plane from the observer which holds planes that want to land or fly
            if (selectedPlane.getState() == selectedPlane.getFlyingState()) {
                clearForLanding(selectedPlane);
            }
        } else if (selectedPlane != null) {
            System.out.println("Airfield is currently occupied, cannot land!");
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

    // used for getting a random location on runway and gate
    public String getRandomNodeID() {
        if (runway != null && !runway.isEmpty()) {
            int numberSelected = (int)(Math.random() * runway.size());
            Node selectedNode = runway.get(numberSelected);
            return selectedNode.getNodeID();
        } else {
            System.out.println("Cannot get a random node ID. Array is null or is empty");
            return null;
        }
    }

    // gives out a flightpath to a selected aircraft
    public ArrayList<Node> SendFlightPath(Aircraft plane) { // notifies airtraffic that they are blocked
        String NodeID = plane.getFlightPath().get(plane.getFlightPath().size()-1).getNodeID();
        Node currentNode = plane.getCurrentNode();
        ArrayList<Node> flightPath = calculateRoute(NodeID, currentNode);
        return  flightPath;
    }
}

