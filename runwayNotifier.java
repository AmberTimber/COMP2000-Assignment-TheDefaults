import java.util.List;
import java.util.ArrayList;

public class runwayNotifier {
    private final List<Aircraft> aircrafts;

    // this class is used to notify aircrafts which are either waiting to fly
    // or are waiting to land
    public runwayNotifier() {
        aircrafts = new ArrayList<>();
    }

    // takes any aircraft
    public <T extends Aircraft> void Subscribe(T selectedAircraft) {
        aircrafts.add(selectedAircraft);
    }

    public <T extends Aircraft> void unSubscribe(T selectedAircraft) {
        aircrafts.remove(selectedAircraft);
    }

    public void Notify(boolean value) {
        // notifies any aircraft the runway is free
        aircrafts.forEach(plane -> plane.RunwayNotice(!value));
    }

    // returns a random aircraft
    public Aircraft getRandomAircraft() {
        return aircrafts.stream().findAny().orElse(null);
    }

    // prevents duplication and also ensures that planes aren't constantly subscribed
    public <T extends Aircraft> boolean checkIfAircraftIsSubscribed(T aircraft) {
        if (aircrafts.contains(aircraft)) {
            return  true;
        } else {
            return  false;
        }
    }
}
