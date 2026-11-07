import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class CommercialPlane extends Aircraft implements flyable {
    private final int numSeats;
    private int currentPassengers;

    public CommercialPlane(String aircraftID, String operator, String model, double fuelLevel, int capacity, int numSeats, int currentPassengers) {
        super(aircraftID, operator, model, fuelLevel, capacity);
        this.numSeats = numSeats;
        this.currentPassengers = currentPassengers;
    }

    //setters
    public void setCurrentPassengers (int currentPassengers) {
        requireInRange(currentPassengers, 0, numSeats, "Passengers");
        this.currentPassengers = currentPassengers;
    }

    //getters 
    public int getNumSeats(){
        return numSeats;
    }

    @Override 
    public int getCurrentCapacity() {
        return currentPassengers;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        
        System.out.println("This is a Commercial Plane.");
        System.out.println("Number of Seats: " + numSeats);
        System.out.println("Current Passengers: " + currentPassengers);
    }

    // for flyable implement
    @Override
    public boolean flying() {
        return "Flying".equals(this.getStatus());
    }

    @Override
    public Vector2 getDestinationPostion() {
        return this.getTarget();
    }
    
    @Override
    public void setLocation(Vector2 newPos) {
        this.setTarget(newPos);
    }

    @Override
    public boolean isReadyForLanding() {
        // Airborne and still has fuel to complete an approach.
        return flying() && getFuelLevel() > 0;
    }

    @Override
    public boolean isReadyForTakeoff() {
        // On the ground, fuelled, and not carrying more than the cabin holds.
        return !flying() && getFuelLevel() > 0 && currentPassengers <= numSeats;
    }

    @Override 
    public void influenceCargo(boolean boarding) {
        if (boarding == false) {
            currentPassengers--;
        } else {
            if (numSeats > currentPassengers) {
                currentPassengers++;
            } else {
                System.out.println("Overcapacity, cannot load anymore");
            }
        }
        // cannot have negative passengers
        if (currentPassengers <= 0) {
            currentPassengers = 0;
        }
    }

    @Override 
    public int getMaxCapacity() {
        return numSeats;
    }

    @Override 
    public String getCargoSummary() {
        return ", No. Passengers: " + getCurrentCapacity();
    }

    @Override
    public String getVehicleType() {
        return "Commercial Plane";
    }

    // passenger windows down both sides of the cabin plus an airline stripe on the tail
    @Override
    protected void drawTypeDetails(Graphics2D canvas, int width, int height) {
        double windowSize = Math.max(1.5, width * 0.025);
        canvas.setColor(new Color(200, 230, 255));
        for (double y = -height * 0.3; y < height * 0.28; y += height * 0.06) {
            canvas.fill(new Ellipse2D.Double(-width * 0.045, y, windowSize, windowSize));
            canvas.fill(new Ellipse2D.Double(width * 0.045 - windowSize, y, windowSize, windowSize));
        }
        canvas.setColor(new Color(200, 30, 30));
        canvas.fill(new Rectangle2D.Double(-width * 0.07, height * 0.36, width * 0.14, height * 0.04));
    }
}
