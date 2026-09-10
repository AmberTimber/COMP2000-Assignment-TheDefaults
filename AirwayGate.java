import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class AirwayGate implements drawable {

    private final String gateID;
    private boolean status;
    private Aircraft currentPlane;
    private Node gateNode;

    public AirwayGate(String gateID, boolean status, Node gateNode) {
        this.gateID = gateID;
        this.status = status;
        this.gateNode = gateNode;
        this.currentPlane = null;
    }

    public void parkPlane(Aircraft plane) throws OccupancyException {

        if (!status) {
            throw new OccupancyException(
                    "Gate " + gateID + " is closed."
            );
        }

        if (!isFree()) {
            throw new OccupancyException(
                    "Gate " + gateID + " is already occupied."
            );
        }

        currentPlane = plane;
        gateNode.setOccupied(true);
    }

    public Aircraft removePlane() {
        Aircraft departingPlane = currentPlane;

        currentPlane = null;
        gateNode.setOccupied(false);

        return departingPlane;
    }

    public boolean isFree() {
        return status && currentPlane == null;
    }

    public String getGateID() {
        return gateID;
    }

    public boolean getStatus() {
        return status;
    }

    public Aircraft getCurrentPlane() {
        return currentPlane;
    }

    public Node getGateNode() {
        return gateNode;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void displayInfo() {
        System.out.println("Gate ID: " + gateID);
        System.out.println("Status: " + (status ? "Open" : "Closed"));
        System.out.println("Node: " + gateNode.getNodeID());

        if (currentPlane != null) {
            System.out.println("Plane at gate: " + currentPlane.getAircraftID());
        } else {
            System.out.println("No plane currently at the gate.");
        }
    }

    //below is Tim's silly works
    public void departingPlane(AirTrafficControl airController, String locationNode) {
        // if plane is docked and timer has reached 0
        if (currentPlane != null && currentPlane.getStatus().equalsIgnoreCase("DOCKED") && currentPlane.CooldownOver() && locationNode != null && !locationNode.isEmpty()) {
            try {
            ArrayList<Node> path = airController.calculateRoute(locationNode, gateNode);
            currentPlane.setFlightPath(path);
            currentPlane.setStatus("GROUNDED");
            currentPlane.setGate(null);
            currentPlane.setDocked(false);
            removePlane();
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception happened while leaving the gate!:" + e);
            } catch (Exception e) {
                System.out.println("An error occured while leaving gate: " + e);
            }
        }
    }

    // check if reached a gate
    public void PlaneAtGate (Aircraft selectedAircraft) {
        if (selectedAircraft != null && selectedAircraft.checkIfEndOfPath() && !selectedAircraft.getStatus().equalsIgnoreCase("DOCKED")) {
                if (selectedAircraft.getPosition().compareVectors(this.getGateNode().getPosition()) && this.getStatus() == true) {
                    try {
                    selectedAircraft.setDocked(true);
                    selectedAircraft.setStatus("DOCKED");
                    int cooldown = (int)(Math.random() * (1000 - 300 + 1)) + 300;
                    selectedAircraft.setCountdown(cooldown); // pretend that people are getting on board + refueling
                    this.parkPlane(selectedAircraft);
                    selectedAircraft.setGate(this);
                    } catch (OccupancyException e) {
                    System.out.println("Occupancy Error at gate: " + e.getMessage());
                    } catch (Exception e) {
                       System.out.println("Error occured at gate: " + e.getMessage()); 
                }
            }
        } else {
            System.out.println("Aircraft not at this gate!"); 
        }
    }

    // for drawing elements of gate
    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawer.setColor(Color.GREEN);
        drawer.fillRect(getGateNode().getXPos(), getGateNode().getYPos(), width, height);
    }
}
