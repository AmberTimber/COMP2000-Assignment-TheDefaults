public class AirwayGate {
    private final String gateID;
    private boolean status; // true if the gate is open, false if closed
    private Aircraft currentPlane; // the plane currently at the gate

    public AirwayGate(String gateID, boolean status, Aircraft currentPlane) {
        this.gateID = gateID;
        this.status = status;
        this.currentPlane = currentPlane;
    }

    //setters
    public boolean parkPlane (Aircraft plane){
        if (status && currentPlane == null){
            currentPlane = plane;
            return true;
        }
        return false;
    }

    public Aircraft removePlane(){
        Aircraft departingPlane = currentPlane;
        currentPlane = null;
        return departingPlane;
    }

    //getters
    public String getGateID() {
        return gateID;
    }

    public boolean getStatus() {
        return status;
    }

    public Aircraft getCurrentPlane() {
        return currentPlane;
    }

    public void displayInfo() {
        System.out.println("Gate ID: " + gateID);
        System.out.println("Status: " + (status ? "Open" : "Closed"));
        if (currentPlane != null) {
            System.out.println("Plane at gate: " + currentPlane.getAircraftID());
        } else {
            System.out.println("No plane currently at the gate.");
        }
    }
}
