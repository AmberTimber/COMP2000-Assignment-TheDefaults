import java.awt.Color;
import java.awt.Graphics;

public class CargoPlane extends Aircraft implements flyable {
    private final double maxWeight; // in KG
    private double currentWeight;

    public CargoPlane(String aircraftID, String operator, String model, double fuelLevel, int capacity, String status, double maxWeight, double currentWeight) {
        super(aircraftID, operator, model, fuelLevel, capacity, status);
        this.maxWeight = maxWeight;
        setCurrentWeight(currentWeight); //call setter from constructor so it would check setter first 
    }

    //setter
    public void setCurrentWeight(double currentWeight){
        if (currentWeight >= 0 && currentWeight <= maxWeight){
            this.currentWeight = currentWeight;
        }
    }
    
    /* No setter for maxWeight because it is fixed when the plane is created */

    //getters
    public double getCurrentWeight(){
        return currentWeight;
    }

    public double getMaxWeight(){
        return maxWeight;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        
        System.out.println("This is a Cargo Plane.");
        System.out.println("Max Weight: " + maxWeight);
        System.out.println("Current Weight: " + currentWeight);
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
        drawer.setColor(Color.RED);
        drawer.fillOval(getXPos(), getYPos(), 50, 50);
        drawer.setColor(Color.black);
        drawer.drawString("CargoPlane: " + this.getAircraftID(), xPos + 5, yPos - 20);
        drawer.drawString("Operator: " + this.getOperator(), xPos + 5, yPos - 5);
        drawer.drawString("Model: " + this.getModel(), xPos + 5, yPos + 10);
        drawer.drawString("Fuel Level: " + this.getFuelLevel(), xPos + 5, yPos + 25);
        drawer.drawString("Capacity: " + this.getCapacity(), xPos + 5, yPos + 40);
        drawer.drawString("Current status: " + this.getStatus(), xPos + 5, yPos + 55);
        drawer.drawString("Current target is: " + getCurrentNode().getNodeID(), xPos + 5, yPos + 70);
    }
}