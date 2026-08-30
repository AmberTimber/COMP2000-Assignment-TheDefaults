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

       /* Aircraft tester = new Aircraft();
        tester.setAircraft("89797", "Billy", "SuperBus", 50.00, 20, "Grounded");*/
        
        //tests by Tim, pls do not move, I need to test JPanel !!!!!!
        JFrame mainPanel = new JFrame();
        mainPanel.setName("This is the simulation"); // ID
        mainPanel.setTitle("Airport Simulation"); // title of tab
        mainPanel.setSize(1000, 800); // size, duh
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure jpanel tab can close
        JPanelVisualizer plaeTemp = new JPanelVisualizer(); // creates objects
        // adding the elements
        mainPanel.add(plaeTemp); // adds objects
        
        mainPanel.setVisible(true); // whoa, i can see clearly now
        
    }
}
