import java.util.ArrayList;

public class AircraftDockedState implements AircraftState {
    private Aircraft aircraft;

    public AircraftDockedState(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    @Override 
    public void Move(AirTrafficControl aircontroller) {
        aircraft.influenceFuel();
        aircraft.decreaseCountdown();
        if (aircraft.CooldownOver()) {
            AirwayGate currentGate = aircraft.getAssignedGate();
            // if plane can depart
            if (aircraft.getAssignedGate() != null && aircraft.getCurrentNode().getPosition().compareVectors(currentGate.getGateNode().getPosition()) && currentGate.getStatus() == true) {
                String selectedNodeID = aircontroller.getRandomNodeID();
                currentGate.departingPlane(aircontroller, selectedNodeID);
                aircraft.setState(aircraft.getGroundedState());
            }
        }
    }

    @Override 
    public void Dock() {
        
    }

    @Override 
    public void FlyNLand(AirTrafficControl aircontroller) {
        System.out.println("Cannot fly while docked");
    }
}
