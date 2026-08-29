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
    private int xPos;
    private int yPos;
    private int xDirection = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private int yDirection = (int)(Math.random() * (10 - 1 + 1)) + 1;
    private Image icon = new ImageIcon("Folder JUMPSCARE/cat.PNG").getImage();

    // intializes time
    public JPanelVisualizer() {
        timer = new Timer(secondsPerFrame, this); // every secondsPerFrame time, = 1 frame
        timer.start(); // starts the timer
        Moveable planePos = new Moveable();
        xPos = (int)planePos.getxPos();
        yPos = (int)planePos.getyPos();
    }

    @Override
    public void actionPerformed(ActionEvent e) { 
        // this entire function is used to update this element every frame
        // key note: increase in xPos = more to right, increase in Y makes it go down
        xPos += xDirection; // moves the thing
        yPos += yDirection;
        if (xPos > getWidth()) { // get width of panel
            xPos = -50; // Loop back to the left side when off-screen
            changeDirection();
        }
        if (yPos > getHeight()) {
            yPos = (int)(Math.random() * (-100 - -50 + -50)) + -50;
            changeDirection();
        }
        
        // updates the panel
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // put anything you want to redraw, like images or shapes here, otherwise they won't be redrawn
        super.paintComponent(g);
        g.drawImage(icon, xPos - 250, yPos - 100, this);
        g.setColor(Color.RED);
        g.fillRect(xPos, yPos, 200, 100); // draw rectangle
        g.setColor(Color.BLACK);
        g.drawString("Hello, this is a template for a plane", xPos + 5, yPos + 50);
    }

    private void changeDirection() {
        xDirection = (int)(Math.random() * (10 - 1 + 1)) + 1;
        yDirection = (int)(Math.random() * (10 - 1 + 1)) + 1;
    }
}
