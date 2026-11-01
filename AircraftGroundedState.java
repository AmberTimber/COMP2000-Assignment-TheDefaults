import java.util.ArrayList;

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
        else if(aircraft.isBlocked() == true && aircraft.canFly() == false) { 
            // if a aircraft path is being blocked, it regenerates a new path or goes back 1 node
            int chosenAction = (int)(Math.random() * 3);
            aircraft.decreaseCountdown();
            if (chosenAction == 0 && aircraft.CooldownOver()) {
                aircraft.reverseAircraft();
            } else if (chosenAction == 1 && aircraft.CooldownOver()) { // generates new path from flight control
                ArrayList<Node> flightPath = AirTrafficControl.getControlTower().SendFlightPath(aircraft);
                if (flightPath == null) { 
                // if path is null, it waits until it can find node or unoccupied
                    aircraft.setCountdown(100);
                }
                if (flightPath != null) {
                    aircraft.setFlightPath(flightPath); // creates new path so it doesn't collide with other aircrafts
                }
            }
        }
        if (aircraft.getReachedTarget() == true && aircraft.getCurrentNode().getNodeTileRepresentation() == Node.locationTypes.AIRFIELD) {
            FlyNLand();
        } else if (aircraft.getReachedTarget() == true && aircraft.getCurrentNode().getNodeTileRepresentation() == Node.locationTypes.GATE) {
            Dock();
        }
    }

    @Override 
    public void Dock() {
        System.out.println("Not currently docking");
    }

    @Override 
    public void FlyNLand() {
        if (aircraft.canFly() == true) {
            // if plane can fly, then it starts to warm up engines before flying
            aircraft.warmUpEngines();
            aircraft.decreaseCountdown();
            if (aircraft.CooldownOver()) {
                aircraft.getFlightPath().get(aircraft.getFlightPath().size()-1).setOccupied(false);
                AirTrafficControl.getControlTower().ClearAircraftForTakeOff(aircraft); // changes path to outside route
                aircraft.setState(aircraft.getFlyingState());
            }
        }
    }
}