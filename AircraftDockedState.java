import java.util.ArrayList;

public class AircraftDockedState implements AircraftState {
    private Aircraft aircraft;
    private int cargoAmount;
    private boolean loading = false;
    private int moveCargoTime = 30;
    private int maxMoveCargoTime;

    public <T extends Aircraft> AircraftDockedState(T aircraft) {
        this.aircraft = aircraft;
        cargoAmount = (int)(Math.random() * aircraft.getMaxCapacity()) + 1;
        maxMoveCargoTime = moveCargoTime;
    }

    @Override 
    public void Move(AirTrafficControl aircontroller) {
        aircraft.influenceFuel();
        // loads or unloads cargo
        if (moveCargoTime <= 0) {
            aircraft.influenceCargo(loading);
            moveCargoTime = maxMoveCargoTime;
        } else {
            moveCargoTime--;
        }
        if (aircraft.getCurrentCapacity() <= 0) {
            loading = true;
        }

        if (aircraft.getCurrentCapacity() >= cargoAmount && loading == true) {
            aircraft.decreaseCountdown(); // wait a certain amount of time before leaving
            if (aircraft.CooldownOver()) {
            AirwayGate currentGate = aircraft.getAssignedGate();
            // if plane can depart
            if (aircraft.getAssignedGate() != null && aircraft.getCurrentNode().getPosition().compareVectors(currentGate.getGateNode().getPosition()) && currentGate.getStatus() == true) {
                String selectedNodeID = aircontroller.getRandomNodeID();
                currentGate.departingPlane(aircontroller, selectedNodeID);
                newCargoAmount();
                moveCargoTime = maxMoveCargoTime;
                aircraft.setState(aircraft.getGroundedState());
            }
            }
        }
        // decorator adding
        aircraft.PlaneStatus = new BoardingDecorator(aircraft.PlaneStatus);
    }

    @Override 
    public void Dock() {
        System.out.println("Aircraft is already docked");
    }

    @Override 
    public void FlyNLand(AirTrafficControl aircontroller) {
        System.out.println("Cannot fly while docked");
    }

    @Override 
    public String getStatus() {
        return "Docked";
    }

    public void newCargoAmount() {
        cargoAmount = (int)(Math.random() * aircraft.getMaxCapacity()) + 1;
        loading = false;
    }
}
