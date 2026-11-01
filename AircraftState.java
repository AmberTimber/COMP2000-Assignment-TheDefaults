public interface AircraftState {
    void Move(AirTrafficControl aircontroller);

    void Dock();

    void FlyNLand(AirTrafficControl aircontroller);
    
}
