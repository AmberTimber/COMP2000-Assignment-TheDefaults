
import java.util.ArrayList;
import java.util.List;

public class AircraftQueriesTest {

    public static void main(String[] args) {

        List<Aircraft> aircraftList = new ArrayList<>();

        aircraftList.add(new CommercialPlane(
                "C101", "TestAir", "Model-A",
                10.0, 180, 150, 50));

        aircraftList.add(new CommercialPlane(
                "C102", "TestAir", "Model-B",
                75.0, 200, 180, 80));

        aircraftList.add(new CommercialPlane(
                "C103", "TestAir", "Model-C",
                5.0, 100, 90, 20));

        List<Aircraft> lowFuelAircraft = AircraftQueries.findLowFuelAircraft(aircraftList);

        System.out.println("Aircraft with less than 20 fuel units:");

        for (Aircraft aircraft : lowFuelAircraft) {
            System.out.println(
                    aircraft.getAircraftID()
                            + " - Fuel: "
                            + aircraft.getFuelLevel());
        }

        // C101 (10.0) and C103 (5.0) are under 20; C102 (75.0) is not
        List<String> foundIds = new ArrayList<>();
        for (Aircraft aircraft : lowFuelAircraft) {
            foundIds.add(aircraft.getAircraftID());
        }
        boolean passed = foundIds.equals(List.of("C101", "C103"))
                && AircraftQueries.findLowFuelAircraft(null).isEmpty();
        System.out.println(passed ? "PASS" : "FAIL - got " + foundIds);
    }
}
