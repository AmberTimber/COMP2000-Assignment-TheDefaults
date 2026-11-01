import java.util.List;
import java.util.ArrayList;

public class runwayNotifier {
    private final List<Aircraft> aircrafts;

    // this class is used to notify aircrafts which are either waiting to fly
    // or are waiting to land
    public runwayNotifier() {
        aircrafts = new ArrayList<>();
    }

    public void Subscribe(Aircraft selectedAircraft) {
        aircrafts.add(selectedAircraft);
        aircrafts.stream().distinct();
    }

    public void unSubscribe(Aircraft selectedAircraft) {
        aircrafts.remove(selectedAircraft);
    }

    public void Notify(boolean value) {
        // notifies any aircraft the runway is free
        aircrafts.forEach(plane -> plane.RunwayNotice(value));
    }

    // returns a random aircraft
    public Aircraft getRandomAircraft() {
        return aircrafts.stream().findAny().orElse(null);
    }

    public boolean checkIfAircraftIsSubscribed(Aircraft aircraft) {
        if (aircrafts.contains(aircraft)) {
            return  true;
        } else {
            return  false;
        }
    }
}
