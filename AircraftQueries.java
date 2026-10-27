
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AircraftQueries {

    // Find aircraft with low fuel without changing their state.
    public static List<Aircraft> findLowFuelAircraft(
            List<Aircraft> aircraftList) {

        if (aircraftList == null) {
            return Collections.emptyList();
        }

        return aircraftList.stream()
                .filter(aircraft -> aircraft != null)
                .filter(aircraft -> aircraft.getFuelLevel() < 20)
                .collect(Collectors.toList());
    }
}
