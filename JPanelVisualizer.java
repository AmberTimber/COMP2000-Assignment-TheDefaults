import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// this entire class is just used to display stuff on JPanel, pls don't delete as it is useful base 
// for drawing stuff

// should probably extend this class to anything with moveable or anything 
// that needs to be drawn or visualized in the simulator
public class JPanelVisualizer extends JPanel implements ActionListener {
    // important for this class
    private Timer timer;
    private int secondsPerFrame = 10; // in miliseconds
    private JFrame JframeRef;
    // Airport
    private ArrayList<Node> flightPath = new ArrayList<>();
    private ArrayList<Node> airportNav = new ArrayList<>();
    // aircrafts
    private int aircraftCount = 10; //(int)(Math.random() * 8) + 1;
    private ArrayList<Aircraft> aircraftsOnSite = new ArrayList<>();
    private ArrayList<Node> runway = new ArrayList<>();
    private ArrayList<AirwayGate> allGates = new ArrayList<>();
    private AirTrafficControl airControl = new AirTrafficControl(aircraftsOnSite, airportNav,new Vector2(400, 600), runway, allGates);
    private ArrayList<Node> outsideLoop = new ArrayList<>();
    private FlightBoard flightBoard = new FlightBoard(aircraftsOnSite);

    // intializes time
    public JPanelVisualizer(JFrame jframePanel) {
        JframeRef = jframePanel;

        Node leftFlyOff = new Node(null, null, null, null, new Vector2(-200, 75), "Outside left", Node.locationTypes.OUTSIDE);
        Node rightFlyOff = new Node(null, null, null, null, new Vector2(JframeRef.getWidth() + 100, 75), "Outside right", Node.locationTypes.AIRFIELD);
        
        Node airfieldNode1 = new Node(null, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 75), "A1", Node.locationTypes.AIRFIELD);
        Node airfieldNode2 = new Node(null, null, airfieldNode1, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 75), "A2", Node.locationTypes.AIRFIELD);
        Node airfieldNode3 = new Node(null, null, airfieldNode2, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 75), "A3", Node.locationTypes.AIRFIELD);
        Node airfieldNode4 = new Node(null, null, airfieldNode3, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 75), "A4", Node.locationTypes.AIRFIELD);

        Node miniRoadNode1 = new Node(airfieldNode1, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 225), "B1", Node.locationTypes.WAITINGBAY);
        Node miniRoadNode2 = new Node(airfieldNode2, null, null, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 225), "B2", Node.locationTypes.WAITINGBAY);
        Node miniRoadNode3 = new Node(airfieldNode3, null, null, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 225), "B3", Node.locationTypes.WAITINGBAY);
        Node miniRoadNode4 = new Node(airfieldNode4, null, null, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 225), "B4", Node.locationTypes.WAITINGBAY);

        Node TaxiWayNode1 = new Node(miniRoadNode1, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 375), "C1", Node.locationTypes.TAXIWAY);
        Node TaxiWayNode2 = new Node(miniRoadNode2, null, TaxiWayNode1, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 375), "C2", Node.locationTypes.TAXIWAY);
        Node TaxiWayNode3 = new Node(miniRoadNode3, null, TaxiWayNode2, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 375), "C3", Node.locationTypes.TAXIWAY);
        Node TaxiWayNode4 = new Node(miniRoadNode4, null, TaxiWayNode3, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 375), "C4", Node.locationTypes.TAXIWAY);

        Node GatePathNode1 = new Node(TaxiWayNode1, null, null, null, new Vector2(JframeRef.getWidth()/9 * 1, 500), "D1", Node.locationTypes.GATEPATH);
        Node GatePathNode2 = new Node(TaxiWayNode2, null, GatePathNode1, null, new Vector2(JframeRef.getWidth()/9 * 3, 500), "D2", Node.locationTypes.GATEPATH);
        Node GatePathNode3 = new Node(TaxiWayNode3, null, GatePathNode2, null, new Vector2(JframeRef.getWidth()/9 * 5, 500), "D3", Node.locationTypes.GATEPATH);
        Node GatePathNode4 = new Node(TaxiWayNode4, null, GatePathNode3, null, new Vector2(JframeRef.getWidth()/9 * 7, 500), "D4", Node.locationTypes.GATEPATH);

        Node TestGate1 = new Node(GatePathNode1, null, null, null, new Vector2(JframeRef.getWidth()/9 * 1, 600), "E1", Node.locationTypes.GATE);
        Node TestGate2 = new Node(GatePathNode2, null, TestGate1, null, new Vector2(JframeRef.getWidth()/9 * 3, 600), "E2", Node.locationTypes.GATE);
        Node TestGate3 = new Node(GatePathNode3, null, TestGate2, null, new Vector2(JframeRef.getWidth()/9 * 5, 600), "E3", Node.locationTypes.GATE);
        Node TestGate4 = new Node(GatePathNode4, null, TestGate3, null, new Vector2(JframeRef.getWidth()/9 * 7, 600), "E4", Node.locationTypes.GATE);

        // setting up map nodes
        airfieldNode1.setBottomNode(miniRoadNode1);
        airfieldNode1.setRightNode(airfieldNode2);
        airfieldNode2.setBottomNode(miniRoadNode2);
        airfieldNode2.setRightNode(airfieldNode3);
        airfieldNode3.setBottomNode(miniRoadNode3);
        airfieldNode3.setRightNode(airfieldNode4);
        airfieldNode4.setBottomNode(miniRoadNode4);

        miniRoadNode1.setBottomNode(TaxiWayNode1);
        miniRoadNode2.setBottomNode(TaxiWayNode2);
        miniRoadNode3.setBottomNode(TaxiWayNode3);
        miniRoadNode4.setBottomNode(TaxiWayNode4);

        TaxiWayNode1.setBottomNode(GatePathNode1);
        TaxiWayNode1.setRightNode(TaxiWayNode2);
        TaxiWayNode2.setBottomNode(GatePathNode2);
        TaxiWayNode2.setRightNode(TaxiWayNode3);
        TaxiWayNode3.setBottomNode(GatePathNode3);
        TaxiWayNode3.setRightNode(TaxiWayNode4);
        TaxiWayNode4.setBottomNode(GatePathNode4);

        GatePathNode1.setBottomNode(TestGate1);
        GatePathNode1.setRightNode(GatePathNode2);
        GatePathNode2.setBottomNode(TestGate2);
        GatePathNode2.setRightNode(GatePathNode3);
        GatePathNode3.setBottomNode(TestGate3);
        GatePathNode3.setRightNode(GatePathNode4);
        GatePathNode4.setBottomNode(TestGate4);

        TestGate1.setRightNode(TestGate2);
        TestGate2.setRightNode(TestGate3);
        TestGate3.setRightNode(TestGate4);

        // add to airport nav
        airportNav.add(airfieldNode1);
        airportNav.add(airfieldNode2);
        airportNav.add(airfieldNode3);
        airportNav.add(airfieldNode4);

        airportNav.add(miniRoadNode1);
        airportNav.add(miniRoadNode2);
        airportNav.add(miniRoadNode3);
        airportNav.add(miniRoadNode4);

        airportNav.add(TaxiWayNode1);
        airportNav.add(TaxiWayNode2);
        airportNav.add(TaxiWayNode3);
        airportNav.add(TaxiWayNode4);

        airportNav.add(GatePathNode1);
        airportNav.add(GatePathNode2);
        airportNav.add(GatePathNode3);
        airportNav.add(GatePathNode4);

        airportNav.add(leftFlyOff);

        // making airport map distinct
        airportNav.stream().distinct();

        // marked runway
        runway.add(airfieldNode1);
        runway.add(airfieldNode2);
        runway.add(airfieldNode3);
        runway.add(airfieldNode4);

        // outside loop
        outsideLoop.add(leftFlyOff);
        outsideLoop.add(rightFlyOff);

        // airway gates
        AirwayGate Gate1 = new AirwayGate("Gate01",true , TestGate1);
        AirwayGate Gate2 = new AirwayGate("Gate02",true , TestGate2);
        AirwayGate Gate3 = new AirwayGate("Gate03",true , TestGate3);
        AirwayGate Gate4 = new AirwayGate("Gate04",true , TestGate4);

        allGates.add(Gate1);
        allGates.add(Gate2);
        allGates.add(Gate3);
        allGates.add(Gate4);

        airControl.setExitNode(leftFlyOff);
        airControl.setEntryNode(rightFlyOff);

        // implementation of random plane generation
        ArrayList<String> firstName = new ArrayList<>();
        firstName.add("Joe");
        firstName.add("Tim");
        firstName.add("Bill");
        firstName.add("Billy");
        firstName.add("Richard");
        firstName.add("Megan");
        firstName.add("Bob");
        firstName.add("Alice");
        firstName.add("Mia");
        List<Integer> randomID = Stream.generate(Math::random).map(num -> (int)Math.round(num) + 1).map(num -> num * (int)((Math.random() * 9999) + 1000)).limit(aircraftCount).collect(Collectors.toList());
        ArrayList<String> randomModel = new ArrayList<>();
        randomModel.add("C-130");
        randomModel.add(" Airbus A350");
        randomModel.add("Boeing 777");
        randomModel.add("Boeing 737");
        randomModel.add("Airbus A220");
        randomModel.add("Cessna 172 Skyhawk");

        // generates aircrafts
        for (int i = 0; i < aircraftCount; i++) {
            int aircraftType = (int)(Math.random() * 2) + 1; // random plane type selection
            int nameSelection = (int)(Math.random() * firstName.size()); // random plane type selection
            int IDselected = (int)(Math.random() * randomID.size());
            int Modelselected = (int)(Math.random() * randomModel.size());
            Aircraft newAircaft = null;
            for (int c = 0; c < aircraftsOnSite.size(); c++) {
                while (randomID.get(IDselected).toString().equalsIgnoreCase(aircraftsOnSite.get(c).getAircraftID())) {
                    // ensure all ID's are unique
                    int newInteger = (int)((Math.random() * 9999) + 1000);
                    randomID.set(IDselected, newInteger);
                    c = 0;
                }
            }
            // selecting type of aircraft
            if (aircraftType == 1) {
                newAircaft = new CargoPlane((randomID.get(IDselected)).toString(), firstName.get(nameSelection), randomModel.get(Modelselected), 5000.0, 2, 600.0, 500.0);
            } else {
                newAircaft = new CommercialPlane((randomID.get(IDselected)).toString(), firstName.get(nameSelection), randomModel.get(Modelselected), 5000, 2, 100, 100);
            }
            
            // setting random spawn on Node, and making it move through the navigation from there
            Node spawningNode = null;
            try {
            spawningNode = airportNav.stream().filter(node -> node.isOccupied == false)
                .filter(node -> node.getNodeTileRepresentation() != Node.locationTypes.AIRFIELD).
                collect(Collectors.collectingAndThen(Collectors.toList(), collectedList -> {
                    int randomIndex = ThreadLocalRandom.current().nextInt(collectedList.size());
                    return collectedList.get(randomIndex);
                }));
            } catch (IllegalArgumentException e) { // if no nodes are free or found in the aircraft ensures it stays null
                System.out.println("No spawning Node found! " + e);
            }

            // sets aircraft to flying immediately if no node is found
            if (spawningNode == null) {
                try {
                    newAircaft.changeTarget();
                    newAircaft.setVector2(newAircaft.getTarget().getPosition());
                    newAircaft.setState(newAircaft.getFlyingState());
                    flightPath = new ArrayList<>(List.of(leftFlyOff));
                    newAircaft.setFlightPath(flightPath);
                    newAircaft.setReachedTarget(true);
                    newAircaft.getFlyingState().RefreshTime();
                } catch (NullPointerException e) {
                    System.out.println("Error occured while making newAircraft become flying state: " + e);
                    newAircaft = null;
                }
            } else {
                // choose between going to gate, going to airfield or already flying
                int chosenBehaviour = (int)(Math.random() * 3) + 1; // random plane to airfield or gate
                AirwayGate selectedGate = allGates.stream().filter(gate -> gate.getAssigned() == false).findFirst().orElse(null);
                Node selectedRunwayNode = runway.stream().filter(n -> !n.getOccupied()).findAny().orElse(null);
                if (selectedGate != null && chosenBehaviour == 1) {
                    try {
                    flightPath = airControl.initialCalculateRoute(selectedGate.getGateNode().getNodeID(), spawningNode);
                    newAircaft.setVector2(spawningNode.getXPos(), spawningNode.getYPos());
                    newAircaft.setFlightPath(flightPath);
                    newAircaft.setColor(getRandomColorRGB());
                    newAircaft.setGate(selectedGate);
                    selectedGate.setAssigned(true);
                    aircraftsOnSite.add(newAircaft);
                    } catch (NullPointerException e) {
                        System.out.println("Error occured while making newAircraft go to gate: " + e);
                        newAircaft = null;
                    }
                } else if (selectedRunwayNode != null && chosenBehaviour == 2) { // becomes airfied goer
                    try {
                    flightPath = airControl.initialCalculateRoute(selectedRunwayNode.getNodeID(), spawningNode);
                    newAircaft.setVector2(spawningNode.getXPos(), spawningNode.getYPos());
                    newAircaft.setFlightPath(flightPath);
                    newAircaft.setColor(getRandomColorRGB());
                    } catch (NullPointerException e) {
                        System.out.println("Error occured while making newAircraft go to runway: " + e);
                        newAircaft = null;
                    }
                } else {
                    try {
                    newAircaft.changeTarget();
                    newAircaft.setVector2(newAircaft.getTarget().getPosition());
                    newAircaft.setState(newAircaft.getFlyingState());
                    flightPath = new ArrayList<>(List.of(leftFlyOff));
                    newAircaft.setFlightPath(flightPath);
                    newAircaft.setReachedTarget(true);
                    newAircaft.getFlyingState().RefreshTime();
                    } catch (NullPointerException e) {
                        System.out.println("Error occured while making newAircraft become flying state: " + e);
                        newAircaft = null;
                    }
                }
            }
                // adds aircrafts to existing list of aircrafts
                if (newAircaft != null && !aircraftsOnSite.contains(newAircaft) && newAircaft.getTarget() != null) {
                    aircraftsOnSite.add(newAircaft);
                }
                
        }

        timer = new Timer(secondsPerFrame, this); // every secondsPerFrame time, = 1 frame
        timer.start(); // starts the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        // this entire function is used to update this element every frame
        // key note: increase in xPos = more to right, increase in Y makes it go down

        for (Aircraft selectedAircraft:aircraftsOnSite) {
            if (selectedAircraft.getFuelLevel() <= 0 && selectedAircraft.getState() == selectedAircraft.getFlyingState()) {
                aircraftsOnSite.remove(selectedAircraft);
                System.err.println("A aircraft has crashed after having no more fuel");
            }

            // checks if the airfield is clear
            airControl.checkIfAAircraftOnAirfield(selectedAircraft);
            for (int i = 0; i < 2; i++) {
            // moves aircraft
            selectedAircraft.Move(airControl);
            airControl.checkIfAAircraftOnAirfield(selectedAircraft);
            }
            // gets any if runway and airport is free
            airControl.GetRandomAircraft();

            airControl.AirfieldNodeChanger();
        }
        
        airControl.freeNodes();
        // updates the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);// put anything drawn after this line
        // background
        g.setColor(new Color(0,100,0));
        g.fillRect(0, 0, JframeRef.getWidth(), JframeRef.getHeight());
        // draw airfield
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, JframeRef.getWidth(), 150);
        g.setColor(Color.white);
        for (int i = 120; i < JframeRef.getWidth() - 130; i+=100) {
            g.drawLine(i, 75, i+50, 75);
        }
        for (int i = 10; i < 120; i+=20) {
            g.fillRect(10, i+10, 100, 10);
            g.fillRect(JframeRef.getWidth() - 130, i+10, 100, 10);
        }
        g.drawLine(0, 10, JframeRef.getWidth(), 10);
        g.drawLine(0, 140, JframeRef.getWidth(), 140);
        // making road to airfield
        g.setColor(Color.GRAY);
        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/7 * i, 150, JframeRef.getWidth()/7-10 , 150);
            }
        }
        g.setColor(Color.YELLOW);
        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                g.drawLine(JframeRef.getWidth()/7 * i + 5, 150, JframeRef.getWidth()/7 * i + 5, 300);
                g.drawLine(JframeRef.getWidth()/7 * i + JframeRef.getWidth()/7-16, 150, JframeRef.getWidth()/7 * i + JframeRef.getWidth()/7-16, 300);
                g.drawLine(JframeRef.getWidth()/7 * i + JframeRef.getWidth()/7/2, 150, JframeRef.getWidth()/7 * i + JframeRef.getWidth()/7/2, 300);
            }
        }
        // making taxiway
        g.setColor(Color.GRAY);
        g.fillRect(0, 300, JframeRef.getWidth(), 150);
        g.setColor(Color.YELLOW);
        for (int i = 0; i < JframeRef.getWidth(); i+=100) {
            g.drawLine(i, 375, i+50, 375);
        }
        g.drawLine(0, 310, JframeRef.getWidth(), 310);
        g.drawLine(0, 440, JframeRef.getWidth(), 440);
        // making road to gate
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < allGates.size(); i++) {
            g.fillRect(allGates.get(i).getGateNode().getXPos() - 100, 450, JframeRef.getWidth()/allGates.size(), 150);
        }

        // making gates
        allGates.stream().forEach(gate -> gate.visualRepresentation(g, JframeRef.getWidth()/9, 200));

        // Making terminal
        g.setColor(Color.BLUE);
        g.fillRect(0, JframeRef.getHeight()-150, JframeRef.getWidth(), 200);
        // air traffic control
        airControl.visualRepresentation(g, 50,50);
        // visualize noeds
        for (int i = 0; i < airportNav.size(); i++) {
            g.setColor(Color.GREEN);
            g.fillOval(airportNav.get(i).getXPos(), airportNav.get(i).getYPos(), 10, 10);
        }

        /* visualize planes by splitting them into groups of flying or not flying
        not flying planes get visualized first then the flying ones, to better help simulate
        the overhead planes being on top*/
        Map<Boolean, List<Aircraft>> seperatedAircrafts = aircraftsOnSite.stream().collect(Collectors.partitioningBy(a -> a.getState() == a.getFlyingState()));
        // createes non-flying aircrafts first
        if (seperatedAircrafts.get(false) != null && !seperatedAircrafts.get(false).isEmpty()) {
            Spliterator<Aircraft> firstHalf = seperatedAircrafts.get(false).spliterator();
            Spliterator<Aircraft> secondHalf = firstHalf.trySplit();
            if (secondHalf != null) {
                secondHalf.forEachRemaining((aircraft) -> aircraft.visualRepresentation(g, 50, 50));
            }
            while (firstHalf.tryAdvance((aircraft) -> aircraft.visualRepresentation(g, 50, 50))) {}
        }
            // creates the flying aircrafts
        if (seperatedAircrafts.get(true) != null && !seperatedAircrafts.get(true).isEmpty()) {
            seperatedAircrafts.get(true).stream().forEach((aircraft) -> aircraft.visualRepresentation(g, 50, 50));
        } 
        // flight board, drawn last so it sits on top of everything else
        // uses this panel's own size (not the JFrame's) so it isn't pushed past the
        // visible area by the title bar/borders, which would clip off the bottom rows
        flightBoard.visualRepresentation(g, getWidth(), getHeight());
    }

    private Color getRandomColorRGB() {
    int r = (int)(Math.random() * 256); // 0 to 255
    int g = (int)(Math.random() * 256);
    int b = (int)(Math.random() * 256);
    
    return new Color(r, g, b);
    }
}
