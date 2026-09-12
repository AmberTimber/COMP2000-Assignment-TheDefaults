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
        requireInRange(currentWeight, 0.0, maxWeight, "Weight");
        this.currentWeight = currentWeight;
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
        // On the ground, fuelled, and not loaded beyond the max weight.
        return !flying() && getFuelLevel() > 0 && currentWeight <= maxWeight;
    }

    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawPlaneShape(drawer, width, height, getColor() != null ? getColor() : Color.RED);
        drawer.setColor(Color.black);
        drawer.drawString("CargoPlane: " + this.getAircraftID(), xPos + 5, yPos - 20);
        drawer.drawString("Operator: " + this.getOperator(), xPos + 5, yPos - 5);
        drawer.drawString("Model: " + this.getModel(), xPos + 5, yPos + 10);
        drawer.drawString("Fuel Level: " + this.getFuelLevel() + "L", xPos + 5, yPos + 25);
        drawer.drawString("Capacity: " + this.getCapacity(), xPos + 5, yPos + 40);
        drawer.drawString("Current status: " + this.getStatus(), xPos + 5, yPos + 55);
        drawer.drawString("Current target is: " + getCurrentNode().getNodeID(), xPos + 5, yPos + 70);
    }

    // allows you to built up the status for future board
    @Override 
    public String getOverallStatus() {
        String status = "Flight number " + getAircraftID();
        return status;
    }
}