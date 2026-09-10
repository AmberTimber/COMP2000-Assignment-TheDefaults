import java.awt.Color;
import java.awt.Graphics;

public class CommercialPlane extends Aircraft implements flyable {
    private final int numSeats;
    private int currentPassengers;

    public CommercialPlane(String aircraftID, String operator, String model, double fuelLevel, int capacity, String status, int numSeats, int currentPassengers) {
        super(aircraftID, operator, model, fuelLevel, capacity, status);
        this.numSeats = numSeats;
        this.currentPassengers = currentPassengers;
    }

    //setters
    public void setCurrentPassengers (int currentPassengers) {
        if (currentPassengers >= 0 && currentPassengers <= numSeats) {
            this.currentPassengers = currentPassengers;
        }
    }

    //getters 
    public int getNumSeats(){
        return numSeats;
    }

    public int getCurrentPassengers(){
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
        if (this.getStatus() == "Flying") {
            return true;
        } else {
            return false;
        }
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
        if (this.getStatus() == "ReadyForLanding") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isReadyForTakeoff() {
        if (this.getStatus() == "ReadyForTakeoff") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawer.setColor(Color.YELLOW);
        drawer.fillOval(getXPos(), getYPos(), 50, 50);
        drawer.setColor(Color.black);
        drawer.drawString("Commercial plane: " + this.getAircraftID(), xPos + 5, yPos - 20);
        drawer.drawString("Operator: " + this.getOperator(), xPos + 5, yPos - 5);
        drawer.drawString("Model: " + this.getModel(), xPos + 5, yPos + 10);
        drawer.drawString("Fuel Level: " + this.getFuelLevel(), xPos + 5, yPos + 25);
        drawer.drawString("Capacity: " + this.getCapacity(), xPos + 5, yPos + 40);
        drawer.drawString("Current status: " + this.getStatus(), xPos + 5, yPos + 55);
        drawer.drawString("Current target is: " + getCurrentNode().getNodeID(), xPos + 5, yPos + 70);
    }
}