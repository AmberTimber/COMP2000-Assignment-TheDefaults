public interface AircraftState {
    void Move(AirTrafficControl aircontroller); // generalised action

    void Dock();

    void FlyNLand(AirTrafficControl aircontroller);
    
    String getStatus(); // for state name getting
}
