import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

    public static void main(String[] args) {
        JFrame mainPanel = new JFrame();
        ImageIcon icon = new ImageIcon("Folder JUMPSCARE/cat.PNG");
        mainPanel.setName("This is a test on the airport");
        mainPanel.setSize(1000, 1000);
        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.add(new JLabel("Hello, this is a template for airport", JLabel.CENTER));
        mainPanel.add(new JLabel(icon, JLabel.CENTER));
        
        mainPanel.setVisible(true);
    }
    

}