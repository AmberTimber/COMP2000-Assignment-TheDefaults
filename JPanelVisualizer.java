import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Image airportRef = new ImageIcon("Folder JUMPSCARE/airportRef.png").getImage();
    private int aircraftCount = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private Aircraft[] aircrafts = new Aircraft[aircraftCount];
    private JFrame JframeRef;

    // intializes time
    public JPanelVisualizer(JFrame jframePanel) {
        JframeRef = jframePanel;
        for (int i = 0; i < aircrafts.length; i++) {
            aircrafts[i] = new CargoPlane("Aircraft " + i, "Harry Potter the " + i, "Hawking404", 30.00, 50,"Fly my minions", 500.00, 250.00);
            int newXpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            int newYpos = (int)(Math.random() * (800 - 1 + 1)) + 1;
            aircrafts[i].setTarget(new Vector2(newXpos, newYpos));
        }

        timer = new Timer(secondsPerFrame, this); // every secondsPerFrame time, = 1 frame
        timer.start(); // starts the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        // this entire function is used to update this element every frame
        // key note: increase in xPos = more to right, increase in Y makes it go down
        for (int i = 0; i < aircrafts.length; i++) {
            aircrafts[i].moveTowards(1);
            if (aircrafts[i].getReachedTarget() == true) {
                aircrafts[i].changeTarget();
                aircrafts[i].setReachedTarget(false);
                System.out.println("New target set!");
            }
            System.out.println("Target location is " + aircrafts[i].getXPos() +"x, " + aircrafts[i].getYPos() + "y. Target pos is " + aircrafts[i].getTarget().getXPos() + "x, " + aircrafts[i].getTarget().getYPos() + "y.");
        }
        
        // updates the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);// put anything drawn after this line
        g.drawImage(airportRef, 0, 0, this); // ref img
        // draw airfield
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, JframeRef.getWidth(), 200);
        g.fillRect(0, 700, JframeRef.getWidth(), 200);
        // air traffic control
        g.drawImage(catImage, JframeRef.getWidth()/2, JframeRef.getHeight()/2 - 200, 150, 150, this);
        // draw plane line
        for (int i = 0; i < aircrafts.length; i++) {
            g.setColor(Color.YELLOW);
            g.fillOval(aircrafts[i].getXPos(), aircrafts[i].getYPos(), 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("This is plane " + aircrafts[i].getAircraftID(), aircrafts[i].getXPos(), aircrafts[i].getYPos());
        }
    }
}
