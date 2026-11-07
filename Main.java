import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== STATUS SIMULATION ==========");
        PlaneStatusDemo();
        System.out.println("========== STATUS SIMULATION COMPLETE ==========\n");
        System.out.println("========== GROUPING SIMULATION ==========");
        planeGrouping();
        System.out.println("========== GROUPING SIMULATION COMPLETE ==========\n");
        
        //tests by Tim, pls do not move, I need to test JPanel !!!!!!
        JFrame mainPanel = new JFrame();
        mainPanel.setName("This is the simulation"); // ID
        mainPanel.setTitle("Airport Simulation"); // title of tab
        mainPanel.setSize(1000, 800); // size, duh
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensure jpanel tab can close
        JPanelVisualizer plaeTemp = new JPanelVisualizer(mainPanel); // creates objects
        // adding the elements
        mainPanel.add(plaeTemp); // adds objects to panel
        
        mainPanel.setVisible(true); // whoa, i can see clearly now
        
    }

    // used to test plane status for future development
    private static void PlaneStatusDemo() {
        Status statusFlight = new CargoPlane("113972", null, null, 0, 0, 0, 0);
        System.out.println(statusFlight.getOverallStatus());
        statusFlight = new DelayedDecorator(statusFlight);
        statusFlight = new BoardingDecorator(statusFlight);
        System.out.println(statusFlight.getOverallStatus());
    }

    private static void planeGrouping() {
        Aircraft DeliveryPlane = new CargoPlane("I6302", null, null, 0, 0, 0, 0);
        Aircraft DeliveryPlane2 = new CargoPlane("ZO22", null, null, 0, 0, 0, 0);
        Aircraft PassengerPlane = new CommercialPlane("TT05", null, null, 0, 0,  0, 0);
        Map<Class<? extends Aircraft>, List<Aircraft>> sortedPlanes = List.of(DeliveryPlane, DeliveryPlane2, PassengerPlane).stream().collect(Collectors.groupingBy(Aircraft::getClass));
        System.out.println(sortedPlanes);
    }
}
