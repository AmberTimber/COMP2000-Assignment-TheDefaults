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
    private Timer timer;
    private int secondsPerFrame = 10; // in miliseconds
    /*private int xPos;
    private int yPos;
    private int xDirection = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private int yDirection = (int)(Math.random() * (10 - 1 + 1)) + 1;*/
    private Image icon = new ImageIcon("Folder JUMPSCARE/cat.PNG").getImage();
    private int aircraftCount = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private Aircraft[] aircrafts = new Aircraft[aircraftCount];

    // intializes time
    public JPanelVisualizer() {
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
                //System.out.println("New target set!");
            }
        }
        
        // updates the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);
        g.drawImage(icon, 100, 20, this);
        for (int i = 0; i < aircrafts.length; i++) {
            g.setColor(Color.YELLOW);
            g.fillOval(aircrafts[i].getLocation().getxPos(), aircrafts[i].getLocation().getyPos(), 50, 50);
            g.setColor(Color.BLACK);
            g.drawString("This is plane " + aircrafts[i].getAircraftID(), aircrafts[i].getLocation().getxPos(), aircrafts[i].getLocation().getyPos());
        }
        /*g.fillRect(xPos, yPos, 200, 100); // draw rectangle
        g.setColor(Color.BLACK);
        g.drawString("Hello, this is a template for a plane", xPos + 5, yPos + 50);*/
    }
}
