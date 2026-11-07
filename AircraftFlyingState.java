public class AircraftFlyingState implements AircraftState {
    private Aircraft aircraft;
    private int flightTime;

    public <T extends Aircraft> AircraftFlyingState(T aircraft) {
        this.aircraft = aircraft;
        flightTime = (int)(Math.random() * (int)aircraft.getFuelLevel()/0.5f) + 3000;
    }
    
    // moves in air
    public void Move(AirTrafficControl aircontroller) {
        if (aircraft.getReachedTarget() == true && aircraft.getFlightPath() != null || aircraft.getTarget() == null) {
            aircraft.getCurrentNode().isOccupied = false;
            aircraft.changeTarget();
        }
        aircraft.moveTowards(1);
        aircraft.influenceFuel();
        flightTime -= 1;
        if (flightTime <= 0) {
            FlyNLand(aircontroller);
            aircraft.PlaneStatus = new DelayedDecorator(aircraft.PlaneStatus);
        }
    }

    public void Dock() {
        System.out.println("Cannot dock while flying");
    }

    public void FlyNLand(AirTrafficControl aircontroller) {
        // if flighttime has finished, then it subscribes to runway observer
        if (aircraft.getChosenToFly()) {
            aircraft.setState(aircraft.getGroundedState());
        } 
        if (aircontroller.GetNotifier().checkIfAircraftIsSubscribed(aircraft) == false) {
            aircontroller.GetNotifier().Subscribe(aircraft);
        }
    }

    public void RefreshTime() {
        flightTime = (int)(Math.random() * (int)aircraft.getFuelLevel()/0.5f) + 3000;
    }

    @Override 
    public String getStatus() {
        return "Flying";
    }
}
