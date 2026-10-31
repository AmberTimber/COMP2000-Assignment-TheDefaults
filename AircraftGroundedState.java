public class AircraftGroundedState implements AircraftState {

    private Aircraft aircraft;

    public AircraftGroundedState(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    @Override 
    public void Move() {
        aircraft.influenceFuel();
        if (aircraft.canFly() == false && aircraft.isBlocked() == false) {
                // moves through the airport
                aircraft.MoveThroughFlightPath(1); 
            } // if blocked while moving 
            else if(aircraft.isBlocked() == true) { 
                // if a aircraft path is being blocked, it regenerates a new path or goes back 1 node
                int chosenAction = (int)(Math.random() * 3);
                if (chosenAction == 0) {
                    aircraft.reverseAircraft();
                } else if (chosenAction == 1) { // generates new path
                    String NodeID = aircraft.getFlightPath().get(aircraft.getFlightPath().size()-1).getNodeID();
                    Node currentNode = aircraft.getCurrentNode();
                    flightPath = airControl.calculateRoute(NodeID, currentNode);
                    if (flightPath == null && aircraft.CooldownOver()) { // if path is null, it waits until it can find node or unoccupied
                        aircraft.setCountdown(100);
                    }
                    if (flightPath != null && aircraft.CooldownOver()) {
                        aircraft.setFlightPath(flightPath); // creates new path so it doesn't collide with other aircrafts
                    }
                }
    }

    @Override 
    public void Dock() {

    }

    @Override 
    public void Fly() {

    }
}