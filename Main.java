import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    public static void main(String[] args) {
        /*Commercial passengerPlane = new Commercial(
                "QF101", "Qantas", "Airbus A320", 82.5, 180, 145);

        Cargo cargoPlane = new Cargo(
                "FX220", "FedEx", "Boeing 767F", 74.0, 3,
                52000.0, 31000.0);

        AirwayGate gate = new AirwayGate("G12", true, null);
        Taxiway taxiway = new Taxiway("T1", 900.0, 1, 20.0);
        Runway runway = new Runway("RWY-07", 3200.0);

        System.out.println("========== AIRPORT SIMULATION ==========");

        // Polymorphism: both objects are stored as Aircraft references.
        Aircraft[] fleet = {passengerPlane, cargoPlane};
        System.out.println("\n1. AIRCRAFT INFORMATION");
        for (Aircraft aircraft : fleet) {
            aircraft.displayInfo();
            System.out.println();
        }

        System.out.println("2. GATE OPERATION");
        boolean assigned = gate.assignAircraft(passengerPlane);
        System.out.println("Passenger aircraft assigned: " + assigned);
        gate.displayInfo();
        Aircraft released = gate.releaseAircraft();
        System.out.println("Released aircraft: "
                + (released == null ? "None" : released.getAircraftID()));

        System.out.println("\n3. TAXIWAY OPERATION");
        System.out.println("Passenger aircraft entered: "
                + taxiway.enterTaxiway(passengerPlane));
        System.out.println("Cargo aircraft entered while full: "
                + taxiway.enterTaxiway(cargoPlane));
        System.out.println("Taxiway occupancy: "
                + taxiway.getCurrentOccupancy() + "/" + taxiway.getCapacity());

        Aircraft readyForTakeoff = taxiway.exitTaxiway();
        System.out.println("Aircraft leaving taxiway: "
                + readyForTakeoff.getAircraftID());

        System.out.println("\n4. RUNWAY OPERATION");
        boolean tookOff = runway.takeOff(readyForTakeoff);
        System.out.println("Take-off successful: " + tookOff);
        System.out.println("Final aircraft status: "
                + readyForTakeoff.getStatus());

        System.out.println("\n5. CARGO OPERATION");
        boolean cargoLoaded = cargoPlane.loadCargo(5000.0);
        System.out.println("Cargo loaded successfully: " + cargoLoaded);
        System.out.println("Cargo weight now: "
                + cargoPlane.getCurrentWeight() + " kg");

        System.out.println("\n========== SIMULATION COMPLETE ==========");*/

        System.out.println("========== TAXIWAY SIMULATION ==========");
        demoTaxiway();
        System.out.println("========== TAXIWAY SIMULATION COMPLETE ==========\n");

        System.out.println("========== COLLECTORS DEMO ==========");
        demoCollectors();
        System.out.println("========== COLLECTORS DEMO COMPLETE ==========\n");

       /* Aircraft tester = new Aircraft();
        tester.setAircraft("89797", "Billy", "SuperBus", 50.00, 20, "Grounded");*/
        
        //tests by Tim, pls do not move, I need to test JPanel !!!!!!
        JFrame mainPanel = new JFrame();
        mainPanel.setName("This is the simulation"); // ID
        mainPanel.setTitle("Airport Simulation"); // title of tab
        mainPanel.setSize(1000, 1000); // size, duh
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure jpanel tab can close
        JPanelVisualizer plaeTemp = new JPanelVisualizer(mainPanel); // creates objects
        // adding the elements
        mainPanel.add(plaeTemp); // adds objects
        
        mainPanel.setVisible(true); // whoa, i can see clearly now
        
    }

    /**
     * Demonstrates Taxiway occupancy tracking:
     * - a plane entering successfully
     * - a second plane being rejected once the taxiway is full
     *   (OccupancyException caught and reported)
     * - occupancy/capacity getters
     * - a plane exiting to free up a spot
     */
    private static void demoTaxiway() {
        CommercialPlane passengerPlane = new CommercialPlane(
                "QF101", "Qantas", "Airbus A320", 82.5, 180, "GROUNDED", 180, 145);

        CargoPlane cargoPlane = new CargoPlane(
                "FX220", "FedEx", "Boeing 767F", 74.0, 3, "GROUNDED", 52000.0, 31000.0);

        // Capacity of 1, so the second aircraft to enter should be rejected.
        Taxiway taxiway = new Taxiway("T1", 900.0, 1, 20.0);

        try {
            boolean entered = taxiway.enterTaxiway(passengerPlane);
            System.out.println("Passenger aircraft entered: " + entered);
        } catch (OccupancyException e) {
            System.out.println("Unexpected occupancy error: " + e.getMessage());
        }

        try {
            boolean entered = taxiway.enterTaxiway(cargoPlane);
            System.out.println("Cargo aircraft entered: " + entered);
        } catch (OccupancyException e) {
            System.out.println("Cargo aircraft rejected: " + e.getMessage());
        }

        System.out.println("Taxiway occupancy: "
                + taxiway.getCurrentOccupancy() + "/" + taxiway.getCapacity());

        Aircraft readyToExit = taxiway.exitTaxiWay();
        System.out.println("Aircraft leaving taxiway: "
                + (readyToExit == null ? "None" : readyToExit.getAircraftID()));

        System.out.println("Taxiway occupancy after exit: "
                + taxiway.getCurrentOccupancy() + "/" + taxiway.getCapacity());

        // Now that there is room again, the cargo plane can enter.
        try {
            boolean entered = taxiway.enterTaxiway(cargoPlane);
            System.out.println("Cargo aircraft entered after space freed: " + entered);
        } catch (OccupancyException e) {
            System.out.println("Unexpected occupancy error: " + e.getMessage());
        }
    }

    /**
     * Demonstrates the Collectors-based reporting on a Taxiway:
     * toList, toSet, toMap, groupingBy (single and multi-level),
     * summarizingDouble, summarizingInt, averagingInt and a custom Collector.of().
     */
    private static void demoCollectors() {
        // Bigger capacity than demoTaxiway so there's something to summarise
        Taxiway taxiway = new Taxiway("T2", 1200.0, 4, 20.0);

        Aircraft[] planes = {
                new CommercialPlane("QF101", "Qantas", "Airbus A320", 82.5, 180, "GROUNDED", 180, 145),
                new CommercialPlane("QF202", "Qantas", "Boeing 737", 64.0, 170, "GROUNDED", 170, 120),
                new CargoPlane("FX220", "FedEx", "Boeing 767F", 74.0, 3, "GROUNDED", 52000.0, 31000.0),
                new CargoPlane("QF900", "Qantas", "Airbus A330F", 90.0, 4, "GROUNDED", 60000.0, 20000.0)
        };

        for (Aircraft plane : planes) {
            try {
                taxiway.enterTaxiway(plane);
            } catch (OccupancyException e) {
                System.out.println("Rejected: " + e.getMessage());
            }
        }

        System.out.println("toList   - IDs: " + taxiway.getAircraftIDs());
        System.out.println("toSet    - Operators: " + taxiway.getOperators());
        System.out.println("toMap    - Lookup FX220: "
                + taxiway.getAircraftByID().get("FX220").getModel());

        System.out.println("groupingBy - by type:");
        taxiway.groupByType().forEach((type, list) ->
                System.out.println("  " + type + ": " + list.size() + " aircraft"));

        System.out.println("groupingBy - by operator, then type:");
        taxiway.groupByOperatorThenType().forEach((operator, byType) -> {
            System.out.println("  " + operator + ":");
            byType.forEach((type, list) ->
                    System.out.println("    " + type + ": " + list.size()));
        });

        DoubleSummaryStatistics fuel = taxiway.getFuelStatistics();
        System.out.println("summarizingDouble - Fuel: min " + fuel.getMin()
                + ", max " + fuel.getMax() + ", avg " + fuel.getAverage());

        IntSummaryStatistics cap = taxiway.getCapacityStatistics();
        System.out.println("summarizingInt - Capacity: min " + cap.getMin()
                + ", max " + cap.getMax() + ", total " + cap.getSum());

        System.out.println("averagingInt - Avg capacity: " + taxiway.getAverageCapacity());

        System.out.println("Collector.of - Manifest:");
        System.out.print(taxiway.getManifest());
    }
}
