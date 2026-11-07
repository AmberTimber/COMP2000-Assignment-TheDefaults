import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class AirwayGate implements drawable {

    private final String gateID;
    private boolean status;
    private Aircraft currentPlane;
    private Node gateNode;
    private boolean isAssigned = false;

    public AirwayGate(String gateID, boolean status, Node gateNode) {
        this.gateID = gateID;
        this.status = status;
        this.gateNode = gateNode;
        this.currentPlane = null;
    }

    /**
 * Parks an aircraft at this gate if the gate is open and free.
 * Throws OccupancyException if the gate is unavailable.
 */

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

    public boolean getAssigned() {
        return isAssigned;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setAssigned(boolean value) {
        isAssigned = value;
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
                gateNode.isOccupied = false;
                ArrayList<Node> path = airController.calculateRoute(locationNode, gateNode);
                if (path == null && currentPlane.CooldownOver()) {
                    int waitingTime = (int)(Math.random() * 2001) + 1000;
                    currentPlane.setCountdown(waitingTime);
                    gateNode.isOccupied = true;
                }
                if (path != null && currentPlane.CooldownOver()) { // ensure path no null, maybe because its blocked, and tries later
                    currentPlane.setFlightPath(path); // creates new path so it doesn't collide with other aircrafts
                    currentPlane.setGate(null);
                    currentPlane.setDocked(false);
                    removePlane(); // removes from gate
                    setAssigned(false);
                }
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception happened while leaving the gate!:" + e);
            } catch (Exception e) {
                System.out.println("An error occured while leaving gate: " + e);
            }
        }
    }

    // check if reached a gate
    public void PlaneAtGate (Aircraft selectedAircraft) {
        if (selectedAircraft != null && selectedAircraft.isAtLastNode() && currentPlane == null) {
                if (selectedAircraft.getPosition().compareVectors(this.getGateNode().getPosition()) && this.getStatus() == true) {
                    try {
                    selectedAircraft.setDocked(true);
                    int cooldown = (int)(Math.random() * 1000) + 600;
                    selectedAircraft.setCountdown(cooldown); // pretend that people are getting on board + refueling
                    this.parkPlane(selectedAircraft);
                    selectedAircraft.setGate(this);
                    gateNode.isOccupied = true;
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
    // pixel-art gate house: a short jet bridge reaching up to the parked plane, the building
    // itself with a dark sign showing the gate ID, and a status light
    // (green = free, amber = assigned to an incoming plane, red = closed/occupied)
    public void visualRepresentation(Graphics drawer, int width, int height) {
        int p = PixelArt.PX;
        // snap to the world pixel grid so the gate lines up with the ground art
        int x = Math.floorDiv(getGateNode().getXPos() - width / 2, p) * p;
        int y = Math.floorDiv(getGateNode().getYPos(), p) * p;
        int w = width / p * p;
        int cx = Math.floorDiv(getGateNode().getXPos(), p) * p;

        // jet bridge: a grey walkway with a darker outline, ending just under the plane's door
        drawer.setColor(new Color(60, 62, 70));
        drawer.fillRect(cx - 20, y + 5, 15, height / 2);
        drawer.setColor(new Color(160, 162, 170));
        drawer.fillRect(cx - 15, y + 5, 5, height / 2 - 5);

        // gate house building
        int top = y + height / 2 - p;
        PixelArt.bevelBox(drawer, x, top, w, y + height - top, p,
                new Color(196, 196, 190), new Color(222, 222, 216), new Color(128, 128, 124));

        // sign with the gate ID in the pixel font
        String label = gateID.toUpperCase();
        int signW = PixelArt.textWidth(label, 2) + 8;
        drawer.setColor(new Color(28, 30, 40));
        drawer.fillRect(x + p * 2, top + p, signW, 14);
        PixelArt.text(drawer, label, x + p * 2 + 4, top + p + 2, 2, new Color(255, 190, 60));

        // status light with a dark rim
        Color light;
        if (!status || currentPlane != null) {
            light = new Color(230, 60, 50);
        } else if (isAssigned) {
            light = new Color(250, 180, 40);
        } else {
            light = new Color(70, 220, 100);
        }
        drawer.setColor(new Color(30, 30, 34));
        drawer.fillRect(x + w - p * 4, top + p, p * 2, p * 2);
        drawer.setColor(light);
        drawer.fillRect(x + w - p * 4 + 2, top + p + 2, p * 2 - 4, p * 2 - 4);
    }
}
