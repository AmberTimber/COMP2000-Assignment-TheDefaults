public class AircraftFlyingState implements AircraftState {
    private Aircraft aircraft;
    private int flightTime = (int)(Math.random() * 2001) + 1000;

    public AircraftFlyingState(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
    
    public void Move() {
        if (flightTime > 0)
        if (aircraft.getReachedTarget() == true && aircraft.getFlightPath() != null) {
            aircraft.getCurrentNode().isOccupied = false;
            aircraft.setFlightPath(null);
        } else if (aircraft.getReachedTarget() == true) {
            aircraft.changeTarget();
        }
        aircraft.moveTowards(1);
        aircraft.checkIfReachTarget();
        aircraft.influenceFuel();
        flightTime -= 1;
        if (flightTime <= 0) {
            FlyNLand();
        }
    }

    void Dock();

    void FlyNLand();

    public void RefreshTime() {
        flightTime = (int)(Math.random() * 2001) + 1000;
    }
}
