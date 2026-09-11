import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

    private int aircraftCount = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private Aircraft[] aircrafts = new Aircraft[1];
    private JFrame JframeRef;
    private ArrayList<Node> flightPath = new ArrayList<>();
    private ArrayList<Node> airportNav = new ArrayList<>();
    private ArrayList<Aircraft> aircraftsOnSite = new ArrayList<>();
    private AirTrafficControl airControl = new AirTrafficControl(aircraftsOnSite,  airportNav,new Vector2(400, 600));
    private ArrayList<Node> runway = new ArrayList<>();
    private ArrayList<Node> waitingBay = new ArrayList<>();
    private ArrayList<Node> outsideLoop = new ArrayList<>();
    private ArrayList<AirwayGate> allGates = new ArrayList<>();

    // intializes time
    public JPanelVisualizer(JFrame jframePanel) {
        JframeRef = jframePanel;
        /*for (int i = 0; i < aircrafts.length; i++) {
            aircrafts[i] = new CargoPlane("Aircraft " + i, "Harry Potter the " + i, "Hawking404", 30.00, 50,"Fly my minions", 500.00, 250.00);
            int newXpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            int newYpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            aircrafts[i].setTarget(new Vector2(newXpos, newYpos));
        }*/
        Node leftFlyOff = new Node(null, null, null, null, new Vector2(-200, 75), "Outside left", "RUNWAY");
        Node leftTopFlyOff = new Node(null, null, null, null, new Vector2(-200, -200), "Outside top left", "Outside");
        Node rightTopFlyOff = new Node(null, null, null, null, new Vector2(JframeRef.getWidth() + 200, -200), "Outside top right", "Outside");
        Node rightFlyOff = new Node(null, null, null, null, new Vector2(JframeRef.getWidth() + 100, 75), "Outside right", "Outside");
        
        Node airfieldNode1 = new Node(null, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 75), "A1", "RUNWAY");
        Node airfieldNode2 = new Node(null, null, airfieldNode1, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 75), "A2", "RUNWAY");
        Node airfieldNode3 = new Node(null, null, airfieldNode2, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 75), "A3", "RUNWAY");
        Node airfieldNode4 = new Node(null, null, airfieldNode3, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 75), "A4", "RUNWAY");

        Node miniRoadNode1 = new Node(airfieldNode1, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 225), "B1", "WAITINGBAY");
        Node miniRoadNode2 = new Node(airfieldNode2, null, null, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 225), "B2", "WAITINGBAY");
        Node miniRoadNode3 = new Node(airfieldNode3, null, null, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 225), "B3", "WAITINGBAY");
        Node miniRoadNode4 = new Node(airfieldNode4, null, null, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 225), "B4", "WAITINGBAY");

        Node TaxiWayNode1 = new Node(miniRoadNode1, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + (JframeRef.getWidth()/7)/2, 375), "C1", "TAXIWAY");
        Node TaxiWayNode2 = new Node(miniRoadNode2, null, TaxiWayNode1, null, new Vector2(JframeRef.getWidth()/7 * 2 + (JframeRef.getWidth()/7)/2, 375), "C2", "TAXIWAY");
        Node TaxiWayNode3 = new Node(miniRoadNode3, null, TaxiWayNode2, null, new Vector2(JframeRef.getWidth()/7 * 4 + (JframeRef.getWidth()/7)/2, 375), "C3", "TAXIWAY");
        Node TaxiWayNode4 = new Node(miniRoadNode4, null, TaxiWayNode3, null, new Vector2(JframeRef.getWidth()/7 * 6 + (JframeRef.getWidth()/7)/2, 375), "C4", "TAXIWAY");

        Node GatePathNode1 = new Node(TaxiWayNode1, null, null, null, new Vector2(JframeRef.getWidth()/9 * 1, 500), "D1", "GATEPATH");
        Node GatePathNode2 = new Node(TaxiWayNode2, null, GatePathNode1, null, new Vector2(JframeRef.getWidth()/9 * 3, 500), "D2", "GATEPATH");
        Node GatePathNode3 = new Node(TaxiWayNode3, null, GatePathNode2, null, new Vector2(JframeRef.getWidth()/9 * 5, 500), "D3", "GATEPATH");
        Node GatePathNode4 = new Node(TaxiWayNode4, null, GatePathNode3, null, new Vector2(JframeRef.getWidth()/9 * 7, 500), "D4", "GATEPATH");

        Node TestGate1 = new Node(GatePathNode1, null, null, null, new Vector2(JframeRef.getWidth()/9 * 1, 600), "E1", "GATE");
        Node TestGate2 = new Node(GatePathNode2, null, TestGate1, null, new Vector2(JframeRef.getWidth()/9 * 3, 600), "E2", "GATE");
        Node TestGate3 = new Node(GatePathNode3, null, TestGate2, null, new Vector2(JframeRef.getWidth()/9 * 5, 600), "E3", "GATE");
        Node TestGate4 = new Node(GatePathNode4, null, TestGate3, null, new Vector2(JframeRef.getWidth()/9 * 7, 600), "E4", "GATE");

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

        // marked runway
        runway.add(airfieldNode1);
        runway.add(airfieldNode2);
        runway.add(airfieldNode3);
        runway.add(airfieldNode4);

        // marked waitingBay
        waitingBay.add(miniRoadNode1);
        waitingBay.add(miniRoadNode2);
        waitingBay.add(miniRoadNode3);
        waitingBay.add(miniRoadNode4);

        // outside loop
        outsideLoop.add(leftFlyOff);
        outsideLoop.add(leftTopFlyOff);
        outsideLoop.add(rightTopFlyOff);
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

        flightPath = airControl.calculateRoute("A4", TestGate1);

        Aircraft testFlight = new CargoPlane("Test aircraft", "Thyme the geat", "Hawking404", 1500.00, 50,"GROUNDED", 500.00, 250.00);
        testFlight.setVector2(200, 400);
        testFlight.setFlightPath(flightPath);
        aircraftsOnSite.add(testFlight);

        Aircraft testFlight2 = new CommercialPlane("Tester103", "Albert Minestein", "Blimper64", 1300.00, 50,"GROUNDED", 500, 250);
        testFlight2.setVector2(TaxiWayNode4.getXPos()-100, TaxiWayNode4.getYPos());
        flightPath = airControl.calculateRoute("A1", TestGate2);
        testFlight2.setFlightPath(flightPath);
        aircraftsOnSite.add(testFlight2);

        Aircraft testFlight3 = new CommercialPlane("12345", "Mr Joel", "AirDuck302",1400.00, 100, "GROUNDED", 1000, 779);
        flightPath = airControl.calculateRoute("E3", miniRoadNode1);
        testFlight3.setVector2(miniRoadNode1.getXPos(), miniRoadNode1.getYPos()-50);
        testFlight3.setFlightPath(flightPath);
        aircraftsOnSite.add(testFlight3);
        

        timer = new Timer(secondsPerFrame, this); // every secondsPerFrame time, = 1 frame
        timer.start(); // starts the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        // this entire function is used to update this element every frame
        // key note: increase in xPos = more to right, increase in Y makes it go down

            for (int i = 0; i < aircraftsOnSite.size(); i++) {
                Aircraft selectedAircraft = aircraftsOnSite.get(i);
                // checks if the airfield is clear
                if (airControl.getOccupiedAirfield() == true) {
                    airControl.checkIfAirfieldIsFree(JframeRef.getWidth(), runway);
                } else {
                airControl.checkIfAAircraftOnAirfield(runway, selectedAircraft);
                }
                // checks if aircraft at gate
                if (selectedAircraft.isAtLastNode() && selectedAircraft.getCurrentNode().getNodeTileRepresentation().equalsIgnoreCase("GATE") && selectedAircraft.getReachedTarget()) {
                    if (selectedAircraft.getAssignedGate() == null) {
                    for (int c = 0; c < allGates.size(); c++) {
                            allGates.get(c).PlaneAtGate(selectedAircraft);
                        }
                    }
                }

                selectedAircraft.influenceFuel(); // either gain fuel or spend fuel
                
                // checks if plane is flying
                if (!selectedAircraft.getFlying()) {
                selectedAircraft.CheckIfNextPathIsBlocked(); // checks if the path is blocked or not
            if (selectedAircraft.canFly() == true) {
                // if plane can fly, then it starts to fly
                selectedAircraft.warmUpEngines();
                selectedAircraft.decreaseCountdown();
                if (selectedAircraft.CooldownOver()) {
                    selectedAircraft.getFlightPath().get(selectedAircraft.getFlightPath().size()-1).setOccupied(false);
                    airControl.ClearAircraftForTakeOff(selectedAircraft, outsideLoop, runway); // changes path to outside route
                }
            } // if on ground
            else if (selectedAircraft.canFly() == false && selectedAircraft.getStatus().equalsIgnoreCase("GROUNDED") && selectedAircraft.isBlocked() == false || selectedAircraft.getChosenToFly()) {
                // moves through the airport
                selectedAircraft.MoveThroughFlightPath(1); 
            } // if blocked while moving 
            else if(selectedAircraft.isBlocked() == true) { 
                // if a aircraft path is being blocked, it regenerates a new path or goes back 1 node
                int chosenAction = (int)(Math.random() * 3);
                if (chosenAction == 0) {
                    selectedAircraft.reverseAircraft();
                } else if (chosenAction == 1) { // generates new path
                    String NodeID = selectedAircraft.getFlightPath().get(selectedAircraft.getFlightPath().size()-1).getNodeID();
                    Node currentNode = selectedAircraft.getCurrentNode();
                    flightPath = airControl.calculateRoute(NodeID, currentNode);
                    selectedAircraft.decreaseCountdown();
                    if (flightPath == null && selectedAircraft.CooldownOver()) { // if path is null, it waits until it can find node or unoccupied
                        selectedAircraft.setCountdown(100);
                    }
                    if (flightPath != null && selectedAircraft.CooldownOver()) {
                        selectedAircraft.getCurrentNode().isOccupied = false;
                        selectedAircraft.setFlightPath(flightPath); // creates new path so it doesn't collide with other aircrafts
                    }
                }
                System.out.println("Changed direction");
            } 
            else if (selectedAircraft.isAtGate()) {
                // if aircraft is at gate, it countsdown until 0
                selectedAircraft.decreaseCountdown();
                if (selectedAircraft.CooldownOver()) {
                    AirwayGate currentGate = selectedAircraft.getAssignedGate();
                    // if plane can depart
                    if (selectedAircraft.getAssignedGate() != null && selectedAircraft.getCurrentNode().getPosition().compareVectors(currentGate.getGateNode().getPosition()) && currentGate.getStatus() == true) {
                        String selectedNodeID = airControl.getRandomNodeID(runway);
                        currentGate.departingPlane(airControl, selectedNodeID);
                    }
                }
            }
             else {
                selectedAircraft.moveTowards(1); // once in flight, moves to target
            }

            // if aircraft is flying
        } else if (selectedAircraft.getFlying()) {
                selectedAircraft.MoveThroughFlightPath(1); 
                // upon looping, it flies back into the airport
                if (selectedAircraft.compareVectors(outsideLoop.get(outsideLoop.size()-1).getPosition())) {
                    // upon raching the airport, finds a gate to go to
                    ArrayList<Node> gateNodes = new ArrayList<>();
                    for (int c = 0; c < allGates.size(); c++) {
                        if (allGates.get(c).isFree()) {
                         gateNodes.add(allGates.get(c).getGateNode());
                        }
                    }
                    String selectedGate = airControl.getRandomNodeID(gateNodes);
                    flightPath = airControl.calculateRoute(selectedGate, runway.get(0));
                    airControl.clearForLanding(selectedAircraft, runway, flightPath);
                }
            }
            else { // otherwise moves towards target
            selectedAircraft.moveTowards(1);
            }

            airControl.AirfieldNodeChanger(runway);
        }
            
        
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
        g.setColor(Color.GRAY);
        for (int i = 0; i < 5; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/5 * i, 450, JframeRef.getWidth()/5 , 150);
            }
        }
        // making gates
        for (int i = 0; i < allGates.size(); i++) {
            allGates.get(i).visualRepresentation(g, JframeRef.getWidth()/9 , 200);
            // g.fillRect(JframeRef.getWidth()/9 * i, 600, JframeRef.getWidth()/9 , 200);
        }
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

        // visualize planes
       for (int i = 0; i < aircraftsOnSite.size(); i++) {
        aircraftsOnSite.get(i).visualRepresentation(g, 50, 50);
       }
    }
}
