import java.util.ArrayList;

public class AircraftGroundedState implements AircraftState {

    private Aircraft aircraft;

    public <T extends Aircraft> AircraftGroundedState(T aircraft) {
        this.aircraft = aircraft;
    }

    @Override 
    public void Move(AirTrafficControl aircontroller) {
        aircraft.influenceFuel();
        aircraft.CheckIfNextPathIsBlocked();
        // moves through the airport
        aircraft.MoveThroughFlightPath(1); 
         // if blocked while moving 
        if(aircraft.isBlocked() == true && !aircraft.getChosenToFly()) { 
            // if a aircraft path is being blocked, it regenerates a new path or goes back 1 node
            int chosenAction = (int)(Math.random() * 3);
            aircraft.decreaseCountdown();
            if (chosenAction == 0 && aircraft.CooldownOver()) {
                aircraft.reverseAircraft();
                aircraft.setCountdown(100);
            } else if (chosenAction == 1 && aircraft.CooldownOver()) { // generates new path from flight control
                ArrayList<Node> flightPath = aircontroller.SendFlightPath(aircraft);
                if (flightPath == null || flightPath.isEmpty()) { 
                // if path is null, it waits until it can find node or unoccupied
                    aircraft.setCountdown(100);
                } else {
                    aircraft.setFlightPath(flightPath); // creates new path so it doesn't collide with other aircrafts
                }
            }
            // if blocked on runay
        } else if (aircraft.isBlocked() == true && aircraft.getChosenToFly()) {
            ArrayList<Node> flightPath = aircontroller.SendFlightPath(aircraft);
            if (flightPath != null) {
                aircraft.setFlightPath(flightPath); // creates new path so it doesn't collide with other aircrafts
            }
    }

        aircraft.CheckIfNextPathIsBlocked();
        if (aircraft.getReachedTarget() == true && 
        aircraft.getCurrentNode().getNodeTileRepresentation() == Node.locationTypes.AIRFIELD &&
         aircraft.getChosenToFly() && aircraft.isAtLastNode()) {
            FlyNLand(aircontroller);
        } else if (aircraft.getReachedTarget() &&
         aircraft.getCurrentNode().getNodeTileRepresentation() == Node.locationTypes.GATE && aircraft.isAtLastNode()) {
            Dock();
        }
    }

    @Override
    public void Dock() {
        // docks aircraft to gate
        if (aircraft.getAssignedGate() != null) {
            aircraft.getAssignedGate().PlaneAtGate(aircraft);
            aircraft.setState(aircraft.getDockedState());
        }
    }

    @Override 
    public void FlyNLand(AirTrafficControl aircontroller) {
        if (aircraft.canFly() == true) {
            // if plane can fly and on runway, then it starts to warm up engines before flying
            aircraft.warmUpEngines();
            aircraft.decreaseCountdown();
            if (aircraft.CooldownOver() && aircraft.getChosenToFly()) {
                aircraft.getFlightPath().get(aircraft.getFlightPath().size()-1).setOccupied(false);
                aircontroller.ClearAircraftForTakeOff(aircraft); // changes path to outside route
                aircraft.getFlyingState().RefreshTime();
                aircraft.setState(aircraft.getFlyingState());
            }
        }
    }

    @Override 
    public String getStatus() {
        return "Grounded";
    }
}