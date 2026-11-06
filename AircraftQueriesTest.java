
import java.util.ArrayList;
import java.util.List;

public class AircraftQueriesTest {

    public static void main(String[] args) {

        List<Aircraft> aircraftList = new ArrayList<>();

        aircraftList.add(new CommercialPlane(
                "C101", "TestAir", "Model-A",
                10.0, 180, "Docked", 150, 50));

        aircraftList.add(new CommercialPlane(
                "C102", "TestAir", "Model-B",
                75.0, 200, "Docked", 180, 80));

        aircraftList.add(new CommercialPlane(
                "C103", "TestAir", "Model-C",
                5.0, 100, "Docked", 90, 20));

        List<Aircraft> lowFuelAircraft = AircraftQueries.findLowFuelAircraft(aircraftList);

        System.out.println("Aircraft with less than 20 fuel units:");

        for (Aircraft aircraft : lowFuelAircraft) {
            System.out.println(
                    aircraft.getAircraftID()
                            + " - Fuel: "
                            + aircraft.getFuelLevel());
        }
    }
}
