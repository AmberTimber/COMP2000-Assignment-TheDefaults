import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
    private int aircraftCount = (int)(Math.random() * 8) + 2;
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
                    flightPath = airControl.calculateRoute(selectedGate.getGateNode().getNodeID(), spawningNode, true);
                    newAircaft.setVector2(spawningNode.getXPos(), spawningNode.getYPos());
                    newAircaft.setFlightPath(flightPath);
                    newAircaft.setColor(getRandomColorRGB());
                    newAircaft.setGate(selectedGate);
                    selectedGate.setAssigned(true);
                    aircraftsOnSite.add(newAircaft);
                    } catch (NullPointerException e) {
                        System.out.println("Error occured while making newAircraft go to gate: " + e);
                        selectedGate.setAssigned(false);
                        selectedGate.getGateNode().isOccupied = false;
                        newAircaft = null;
                    }
                } else if (selectedRunwayNode != null && chosenBehaviour == 2) { // becomes airfied goer
                    try {
                    flightPath = airControl.calculateRoute(selectedRunwayNode.getNodeID(), spawningNode, true);
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
                if (newAircaft != null && !aircraftsOnSite.contains(newAircaft) && newAircaft.getTarget() != null && newAircaft.getCurrentNode() != null) {
                    aircraftsOnSite.add(newAircaft);
                }
                
        }

        airportNav.forEach(N -> N.setOccupied(false));

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

    // ---------- pixel-art runway (strip across the top of the screen, y 0-150) ----------
    // everything is drawn on a coarse grid of PX x PX "pixels" with a small palette so it
    // reads as 2D pixel art. the static parts (tarmac, paint, skid marks) are rendered once
    // into an image and reused every frame; only the edge lights are redrawn so they can animate
    private static final int PX = 5;
    private static final int RUNWAY_ROWS = 150 / PX; // 30 rows tall

    private static final Color TARMAC = new Color(58, 58, 66);
    private static final Color TARMAC_LIGHT = new Color(68, 68, 77);
    private static final Color TARMAC_DARK = new Color(49, 49, 56);
    private static final Color SKID = new Color(30, 30, 34);
    private static final Color CONCRETE = new Color(104, 104, 98);
    private static final Color CONCRETE_DARK = new Color(88, 88, 83);
    private static final Color PAINT = new Color(236, 236, 226);
    private static final Color PAINT_WORN = new Color(190, 190, 182);
    private static final Color LIGHT_DIM = new Color(200, 150, 50);
    private static final Color LIGHT_BRIGHT = new Color(255, 236, 140);
    private static final Color LIGHT_GLOW = new Color(255, 210, 90, 170);

    // 3x5 pixel font for the runway numbers, one string per digit read row by row
    private static final String[] PIXEL_DIGITS = {
        "111101101101111", "010110010010111", "111001111100111", "111001111001111", "101101111001001",
        "111100111001111", "111100111101111", "111001010010010", "111101111101111", "111101111001111"
    };

    // static runway art (tarmac, paint, skid marks); drawn into the cached ground image
    private void drawRunwayStatic(Graphics g, int width) {
        int cols = (width + PX - 1) / PX;

        // speckled tarmac, with a concrete shoulder along the top and bottom rows
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row < RUNWAY_ROWS; row++) {
                int h = pixelHash(col, row);
                Color c;
                if (row == 0 || row == RUNWAY_ROWS - 1) {
                    c = h % 5 == 0 ? CONCRETE_DARK : CONCRETE;
                } else {
                    c = h % 9 == 0 ? TARMAC_LIGHT : h % 11 == 0 ? TARMAC_DARK : TARMAC;
                }
                setPixel(g, col, row, c);
            }
        }

        // a few short diagonal cracks scattered along the runway
        for (int col = 30; col < cols - 30; col += 19) {
            int h = pixelHash(col, 99);
            if (h % 3 != 0) continue;
            int row = 4 + h % 21;
            for (int i = 0; i < 3; i++) {
                setPixel(g, col + i, row + (h % 2 == 0 ? i : -i), TARMAC_DARK);
            }
        }

        // tyre skid marks where planes touch down at each end
        for (int start : new int[] {36, cols - 51}) {
            for (int row : new int[] {12, 13, 17, 18}) {
                for (int col = start; col < start + 15; col++) {
                    if (pixelHash(col, row) % 4 != 0) setPixel(g, col, row, SKID);
                }
            }
        }

        // edge lines
        paintCells(g, 0, 2, cols, 1);
        paintCells(g, 0, RUNWAY_ROWS - 3, cols, 1);

        // threshold "piano keys" at both ends
        for (int bar = 0; bar < 6; bar++) {
            int row = 4 + bar * 4;
            paintCells(g, 2, row, 20, 2);
            paintCells(g, cols - 22, row, 20, 2);
        }

        // runway numbers in the pixel font
        drawPixelNumber(g, "09", 24, 13);
        drawPixelNumber(g, "27", cols - 31, 13);

        // touchdown zone markings either side of the centre line
        for (int col : new int[] {35, 47, cols - 43, cols - 55}) {
            paintCells(g, col, 8, 8, 2);
            paintCells(g, col, 20, 8, 2);
        }

        // dashed centre line
        for (int col = 35; col + 9 <= cols - 35; col += 16) {
            paintCells(g, col, 14, 9, 2);
        }
    }

    // edge lights along both shoulders; a brighter light "chases" along them from left to right
    private void drawRunwayLights(Graphics g, int width) {
        int cols = (width + PX - 1) / PX;
        int lightCount = (cols - 4) / 10 + 1;
        int chase = (int) ((System.currentTimeMillis() / 90) % lightCount);
        for (int i = 0; i < lightCount; i++) {
            int col = 4 + i * 10;
            boolean lit = i == chase || i == (chase + lightCount - 1) % lightCount;
            for (int row : new int[] {0, RUNWAY_ROWS - 1}) {
                if (lit) {
                    // small plus-shaped glow around the bright light
                    setPixel(g, col - 1, row, LIGHT_GLOW);
                    setPixel(g, col + 1, row, LIGHT_GLOW);
                    setPixel(g, col, row == 0 ? 1 : row - 1, LIGHT_GLOW);
                }
                setPixel(g, col, row, lit ? LIGHT_BRIGHT : LIGHT_DIM);
            }
        }
    }

    private void drawPixelNumber(Graphics g, String number, int col, int row) {
        for (int d = 0; d < number.length(); d++) {
            String glyph = PIXEL_DIGITS[number.charAt(d) - '0'];
            for (int i = 0; i < glyph.length(); i++) {
                if (glyph.charAt(i) == '1') {
                    paintCells(g, col + d * 4 + i % 3, row + i / 3, 1, 1);
                }
            }
        }
    }

    // white runway paint, with the odd worn-looking pixel
    private void paintCells(Graphics g, int col, int row, int w, int h) {
        paintCells(g, col, row, w, h, PAINT, PAINT_WORN);
    }

    private void paintCells(Graphics g, int col, int row, int w, int h, Color paint, Color worn) {
        for (int x = col; x < col + w; x++) {
            for (int y = row; y < row + h; y++) {
                setPixel(g, x, y, pixelHash(x, y) % 7 == 0 ? worn : paint);
            }
        }
    }

    // same as paintCells but takes a rectangle in screen pixels and snaps it to the grid
    private void paintArea(Graphics g, int x, int y, int w, int h, Color paint, Color worn) {
        int col = Math.floorDiv(x, PX);
        int row = Math.floorDiv(y, PX);
        int cols = Math.max(1, Math.floorDiv(x + w - 1, PX) - col + 1);
        int rows = Math.max(1, Math.floorDiv(y + h - 1, PX) - row + 1);
        paintCells(g, col, row, cols, rows, paint, worn);
    }

    private void setPixel(Graphics g, int col, int row, Color color) {
        PixelArt.cell(g, col, row, color);
    }

    private static int pixelHash(int x, int y) {
        return PixelArt.hash(x, y);
    }

    // ---------- rest of the ground: grass, connector roads, taxiway, gate aprons, terminal ----------
    // all static, so it's rendered once (together with the runway) into groundCache
    private static final Color[] GRASS = {
        new Color(70, 130, 58), new Color(80, 145, 64), new Color(61, 116, 51), new Color(92, 156, 70)
    };
    private static final Color GRASS_TUFT = new Color(112, 176, 82);
    private static final Color[] TAXI_TARMAC = { new Color(78, 78, 84), new Color(88, 88, 95), new Color(68, 68, 74) };
    private static final Color[] APRON = { new Color(170, 168, 160), new Color(180, 178, 170), new Color(158, 156, 149) };
    private static final Color APRON_SEAM = new Color(142, 140, 134);
    private static final Color TAXI_YELLOW = new Color(234, 190, 42);
    private static final Color TAXI_YELLOW_WORN = new Color(192, 158, 44);
    private static final Color[] TERMINAL_ROOF = { new Color(74, 84, 112), new Color(82, 93, 122), new Color(66, 75, 100) };
    private static final Color TERMINAL_RIDGE = new Color(92, 104, 134);
    private static final Color GLASS = new Color(122, 190, 226);
    private static final Color GLASS_SHINE = new Color(196, 232, 250);
    private static final Color WINDOW_FRAME = new Color(38, 46, 66);

    private BufferedImage groundCache = null;
    private int groundCacheWidth = -1;
    private int groundCacheHeight = -1;

    private void drawGround(Graphics g, int width, int height) {
        if (groundCache == null || groundCacheWidth != width || groundCacheHeight != height) {
            groundCache = buildGroundImage(width, height); // rebuild only if the window was resized
            groundCacheWidth = width;
            groundCacheHeight = height;
        }
        g.drawImage(groundCache, 0, 0, null);
    }

    private BufferedImage buildGroundImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // grass everywhere, with the odd brighter tuft
        PixelArt.texture(g, 0, 0, width, height, GRASS, 0);
        for (int col = 0; col < width / PX; col++) {
            for (int row = 0; row < height / PX; row++) {
                if (pixelHash(col * 3, row * 7) % 47 == 0) {
                    setPixel(g, col, row, GRASS_TUFT);
                    setPixel(g, col + 1, row - 1, GRASS_TUFT);
                }
            }
        }

        drawRunwayStatic(g, width);
        drawConnectorRoads(g, width);
        drawTaxiway(g, width);
        drawAprons(g);
        drawTerminal(g, width, height);

        g.dispose();
        return image;
    }

    // short roads joining the runway (y 150) to the taxiway (y 300), same spots as the B nodes
    private void drawConnectorRoads(Graphics g, int width) {
        int seg = width / 7;
        for (int i = 0; i < 8; i += 2) {
            int x = seg * i;
            int w = seg - 10;
            PixelArt.texture(g, x, 150, w, 150, TAXI_TARMAC, 7);
            // edge lines
            paintArea(g, x + 5, 150, PX, 150, TAXI_YELLOW, TAXI_YELLOW_WORN);
            paintArea(g, x + w - 10, 150, PX, 150, TAXI_YELLOW, TAXI_YELLOW_WORN);
            // dashed centre line leading to the runway
            for (int y = 205; y < 300; y += 30) {
                paintArea(g, x + seg / 2 - 2, y, PX, 15, TAXI_YELLOW, TAXI_YELLOW_WORN);
            }
            // hold-short marking: two dashed lines (runway side) then two solid lines
            for (int lineY : new int[] {155, 165}) {
                for (int dx = 15; dx < w - 15; dx += 15) {
                    paintArea(g, x + dx, lineY, 10, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
                }
            }
            paintArea(g, x + 15, 175, w - 30, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
            paintArea(g, x + 15, 185, w - 30, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
        }
    }

    // main taxiway band across the middle (y 300-450)
    private void drawTaxiway(Graphics g, int width) {
        PixelArt.texture(g, 0, 300, width, 150, TAXI_TARMAC, 13);
        paintArea(g, 0, 310, width, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
        paintArea(g, 0, 435, width, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
        for (int x = 0; x < width; x += 100) {
            paintArea(g, x, 375, 50, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
        }
    }

    // concrete stands in front of each gate (y 450-600) with slab seams and a yellow lead-in line
    private void drawAprons(Graphics g) {
        if (allGates.isEmpty()) {
            return;
        }
        int apronWidth = JframeRef.getWidth() / allGates.size();
        for (AirwayGate gate : allGates) {
            int gateX = gate.getGateNode().getXPos();
            int x = gateX - 100;
            PixelArt.texture(g, x, 450, apronWidth, 150, APRON, 21);
            // slab seams every 6 cells
            for (int col = Math.floorDiv(x, PX); col <= Math.floorDiv(x + apronWidth - 1, PX); col++) {
                for (int row = 450 / PX; row < 600 / PX; row++) {
                    if (col % 6 == 0 || row % 6 == 0) {
                        setPixel(g, col, row, APRON_SEAM);
                    }
                }
            }
            // lead-in line down to the stand, and a stop bar where the plane parks
            paintArea(g, gateX - 2, 450, PX, 135, TAXI_YELLOW, TAXI_YELLOW_WORN);
            paintArea(g, gateX - 15, 585, 30, PX, TAXI_YELLOW, TAXI_YELLOW_WORN);
        }
    }

    // terminal building along the bottom: glass frontage, ribbed roof, rooftop AC units and a sign
    private void drawTerminal(Graphics g, int width, int height) {
        int top = height - 150;
        int topRow = Math.floorDiv(top, PX);
        int cols = (width + PX - 1) / PX;
        PixelArt.texture(g, 0, top, width, 150, TERMINAL_ROOF, 31);

        // ribbed roof
        for (int row = topRow + 5; row < height / PX; row += 4) {
            for (int col = 0; col < cols; col++) {
                setPixel(g, col, row, TERMINAL_RIDGE);
            }
        }

        // glass frontage facing the apron: frame row, two rows of windows, frame row
        for (int col = 0; col < cols; col++) {
            setPixel(g, col, topRow, WINDOW_FRAME);
            setPixel(g, col, topRow + 3, WINDOW_FRAME);
            boolean mullion = col % 4 == 3;
            setPixel(g, col, topRow + 1, mullion ? WINDOW_FRAME : (col % 4 == 0 ? GLASS_SHINE : GLASS));
            setPixel(g, col, topRow + 2, mullion ? WINDOW_FRAME : GLASS);
        }

        // rooftop AC units scattered along the roof
        Map<Character, Color> acPalette = Map.of(
            'o', new Color(40, 40, 46), 'g', new Color(150, 152, 158), 'l', new Color(190, 192, 198), 'f', new Color(70, 72, 80));
        String[] acUnit = {
            "oooooo",
            "ollllo",
            "olffgo",
            "olffgo",
            "oooooo"
        };
        for (int col = 40; col < cols - 10; col += 23) {
            int h = pixelHash(col, 7);
            if (h % 3 == 0) continue;
            PixelArt.sprite(g, acUnit, col * PX, (topRow + 7 + h % 12) * PX, PX, acPalette);
        }

        // terminal name painted on the roof
        PixelArt.shadowText(g, "TERMINAL 1", 20, top + 35, 4, new Color(236, 232, 214), new Color(30, 34, 48));
    }

    // small green taxi-guidance light for each navigation node, centred on the node
    private void drawNavNode(Graphics g, Node node) {
        int x = node.getXPos();
        int y = node.getYPos();
        g.setColor(new Color(18, 60, 28));
        g.fillRect(x - 4, y - 4, 10, 10);
        g.setColor(new Color(70, 200, 100));
        g.fillRect(x - 2, y - 2, 6, 6);
        g.setColor(new Color(190, 255, 200));
        g.fillRect(x - 2, y - 2, 2, 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);// put anything drawn after this line
        // static ground (grass, runway, taxiways, aprons, terminal), cached as one image
        drawGround(g, JframeRef.getWidth(), JframeRef.getHeight());
        // animated runway edge lights
        drawRunwayLights(g, JframeRef.getWidth());

        // making gates (gate houses sit between the aprons and the terminal, y 600-650)
        allGates.stream().forEach(gate -> gate.visualRepresentation(g, JframeRef.getWidth()/9, 50));

        // air traffic control
        airControl.visualRepresentation(g, 50,50);
        // visualize nodes
        for (int i = 0; i < airportNav.size(); i++) {
            drawNavNode(g, airportNav.get(i));
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
