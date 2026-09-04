import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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

    private Image catImage = new ImageIcon("Folder JUMPSCARE/cat.PNG").getImage();
    private int aircraftCount = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private Aircraft[] aircrafts = new Aircraft[1];
    private JFrame JframeRef;
    private ArrayList<Vector2> flightPath = new ArrayList<>();
    private Aircraft testFlight;
    private int count = 0;

    // intializes time
    public JPanelVisualizer(JFrame jframePanel) {
        JframeRef = jframePanel;
        /*for (int i = 0; i < aircrafts.length; i++) {
            aircrafts[i] = new CargoPlane("Aircraft " + i, "Harry Potter the " + i, "Hawking404", 30.00, 50,"Fly my minions", 500.00, 250.00);
            int newXpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            int newYpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            aircrafts[i].setTarget(new Vector2(newXpos, newYpos));
        }*/

        Node airfieldNode1 = new Node(null, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + JframeRef.getWidth()/7, 75), "A1");
        Node airfieldNode2 = new Node(null, null, airfieldNode1, null, new Vector2(JframeRef.getWidth()/7 * 1 + JframeRef.getWidth()/7, 75), "A2");
        Node airfieldNode3 = new Node(null, null, airfieldNode2, null, new Vector2(JframeRef.getWidth()/7 * 2 + JframeRef.getWidth()/7, 75), "A3");
        Node airfieldNode4 = new Node(null, null, airfieldNode3, null, new Vector2(JframeRef.getWidth()/7 * 3 + JframeRef.getWidth()/7, 75), "A4");

        Node miniRoadNode1 = new Node(airfieldNode1, null, null, null, new Vector2(JframeRef.getWidth()/7 * 0 + JframeRef.getWidth()/7, 225), "B1");
        Node miniRoadNode2 = new Node(airfieldNode2, null, miniRoadNode1, null, new Vector2(JframeRef.getWidth()/7 * 1 + JframeRef.getWidth()/7, 225), "B2");
        Node miniRoadNode3 = new Node(airfieldNode3, null, miniRoadNode2, null, new Vector2(JframeRef.getWidth()/7 * 2 + JframeRef.getWidth()/7, 225), "B3");
        Node miniRoadNode4 = new Node(airfieldNode4, null, miniRoadNode3, null, new Vector2(JframeRef.getWidth()/7 * 3 + JframeRef.getWidth()/7, 225), "B4");

        Node TaxiWayNode1 = new Node(airfieldNode1, null, null, null, new Vector2(JframeRef.getWidth()/5 * 0 + JframeRef.getWidth()/5, 525), "C1");

        airfieldNode1.setBottomNode(miniRoadNode1);
        airfieldNode1.setRightNode(airfieldNode2);
        miniRoadNode1.setBottomNode(TaxiWayNode1);
        miniRoadNode1.setRightNode(miniRoadNode2);
        miniRoadNode2.setRightNode(miniRoadNode3);


        flightPath = TaxiWayNode1.findNode("A2", flightPath);

        testFlight = new CargoPlane("Test aircraft", "Time the greek", "Hawking404", 30.00, 50,"Fly my minions", 500.00, 250.00);
        testFlight.setVector2(JframeRef.getWidth()/9 + (JframeRef.getWidth()/9)/2, 600);
        testFlight.setTarget(TaxiWayNode1.getPosition());

        if (flightPath != null && !flightPath.isEmpty()) {
        for (int i = 0; i < flightPath.size(); i++) {
            System.out.println(flightPath.get(i).xPos + ", " + flightPath.get(i).yPos);
        }
    } else {
        System.out.println("This node stuff isn't working yo, it can't find path");
    }
    System.out.println(airfieldNode1.rightNode.NodeID);

        timer = new Timer(secondsPerFrame, this); // every secondsPerFrame time, = 1 frame
        timer.start(); // starts the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        // this entire function is used to update this element every frame
        // key note: increase in xPos = more to right, increase in Y makes it go down
        /*for (int i = 0; i < aircrafts.length; i++) {
            aircrafts[i].moveTowards(1);
            if (aircrafts[i].getReachedTarget() == true) {
                aircrafts[i].changeTarget();
                aircrafts[i].setReachedTarget(false);
                System.out.println("New target set!");
            }
            System.out.println("Target location is " + aircrafts[i].getXPos() +"x, " + aircrafts[i].getYPos() + "y. Target pos is " + aircrafts[i].getTarget().getXPos() + "x, " + aircrafts[i].getTarget().getYPos() + "y.");
        }*/

            if (flightPath.size() >= count) {
            testFlight.setTarget(flightPath.get(count).getVector2());
            testFlight.moveTowards(1);
    
             if (testFlight.getReachedTarget() == true) {
                count++;
                testFlight.setTarget(flightPath.get(count).getVector2());
                testFlight.setReachedTarget(false);
                System.out.println("New target set!");
            }
        }
            
        
        // updates the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);// put anything drawn after this line
        // draw airfield
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, JframeRef.getWidth(), 150);
        g.setColor(Color.white);
        g.drawLine(0, 75, JframeRef.getWidth(), 75);
        // making road to airfield
        g.setColor(Color.GRAY);
        for (int i = 0; i < 8; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/7 * i, 150, JframeRef.getWidth()/7 , 150);
            }
        }
        // making taxiway
        g.fillRect(0, 300, JframeRef.getWidth(), 150);
        // making road to gate
        for (int i = 0; i < 5; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/5 * i, 450, JframeRef.getWidth()/5 , 150);
            }
        }
        // making gates
        g.setColor(Color.GREEN);
        for (int i = 0; i < 10; i++) {
            if (i%2 == 0) {
                g.fillRect(JframeRef.getWidth()/9 * i, 600, JframeRef.getWidth()/9 , 200);
            }
        }
        // Making terminal
        g.setColor(Color.BLUE);
        g.fillRect(0, 800, JframeRef.getWidth(), 200);
        // air traffic control
        g.drawImage(catImage, JframeRef.getWidth()/2, JframeRef.getHeight() - 300, 150, 150, this);
        // draw plane line
        /*for (int i = 0; i < aircrafts.length; i++) {
            g.setColor(Color.YELLOW);
            g.fillOval(aircrafts[i].getXPos(), aircrafts[i].getYPos(), 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("This is plane " + aircrafts[i].getAircraftID(), aircrafts[i].getXPos(), aircrafts[i].getYPos());
        }*/
        g.setColor(Color.YELLOW);
        g.fillOval(testFlight.getXPos(), testFlight.getYPos(), 50, 50);
    }
}
