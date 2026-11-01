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
    }

    public void unSubscribe(Aircraft selectedAircraft) {
        aircrafts.remove(selectedAircraft);
    }

    public void Notify(boolean value) {
        // notifies any aircraft the runway is free
        aircrafts.forEach(plane -> plane.RunwayNotice(value));
    }
}
