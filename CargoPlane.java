import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

public class CargoPlane extends Aircraft implements flyable {
    private final double maxWeight; // in KG
    private double currentWeight;

    public CargoPlane(String aircraftID, String operator, String model, double fuelLevel, int capacity, double maxWeight, double currentWeight) {
        super(aircraftID, operator, model, fuelLevel, capacity);
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
    @Override 
    public int getCurrentCapacity() {
        return (int)currentWeight;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        
        System.out.println("This is a Cargo Plane.");
        System.out.println("Max Weight: " + maxWeight);
        System.out.println("Current Weight: " + currentWeight);
    }

    @Override 
    public String getVehicleType() {
        return "CargoPlane";
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
    public void influenceCargo(boolean reStocking) {
        int randomValueChange = (int)(Math.random() * 31) + 1;
        if (reStocking == false) {
            currentWeight -= randomValueChange;
        } else {
            if (currentWeight < maxWeight) {
                currentWeight += randomValueChange;
            } else {
                System.out.println("Overcapacity, cannot load anymore");
            }
        }
        // ensure cannot be negative number
        if (currentWeight <= 0) {
            currentWeight = 0;
        }
    }

    @Override 
    public int getMaxCapacity() {
        return (int)maxWeight;
    }

    @Override
    public String getCargoSummary() {
        return ", Cargo capacity: " + getCurrentCapacity() + "KG";
    }

    // cargo planes have a chunkier fuselage to fit the freight
    @Override
    protected Shape getFuselageShape(int width, int height) {
        return new RoundRectangle2D.Double(-width * 0.1, -height * 0.5, width * 0.2, height, width * 0.2, height * 0.3);
    }

    // four engines, two under each wing
    @Override
    protected void drawEngines(Graphics2D canvas, int width, int height, Color outline) {
        drawEngine(canvas, -width * 0.2, -height * 0.03, width, height, outline);
        drawEngine(canvas, -width * 0.36, height * 0.04, width, height, outline);
        drawEngine(canvas, width * 0.2, -height * 0.03, width, height, outline);
        drawEngine(canvas, width * 0.36, height * 0.04, width, height, outline);
    }

    // hazard-striped cargo door on top and a load bar showing how full the hold is
    @Override
    protected void drawTypeDetails(Graphics2D canvas, int width, int height) {
        double doorX = -width * 0.07;
        double doorY = -height * 0.28;
        double doorW = width * 0.14;
        double doorH = height * 0.14;
        canvas.setColor(new Color(240, 190, 0));
        canvas.fill(new Rectangle2D.Double(doorX, doorY, doorW, doorH));
        canvas.setColor(Color.BLACK);
        canvas.draw(new Rectangle2D.Double(doorX, doorY, doorW, doorH));
        canvas.draw(new Line2D.Double(doorX, doorY + doorH / 2, doorX + doorW, doorY + doorH / 2));

        // load indicator along the spine: dark track, green fill proportional to currentWeight
        double barX = -width * 0.03;
        double barY = -height * 0.08;
        double barW = width * 0.06;
        double barH = height * 0.36;
        double fraction = maxWeight > 0 ? Math.min(1.0, currentWeight / maxWeight) : 0;
        canvas.setColor(new Color(50, 50, 50));
        canvas.fill(new Rectangle2D.Double(barX, barY, barW, barH));
        canvas.setColor(new Color(60, 200, 80));
        canvas.fill(new Rectangle2D.Double(barX, barY + barH * (1 - fraction), barW, barH * fraction));
    }
}
