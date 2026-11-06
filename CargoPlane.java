
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
}