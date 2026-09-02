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
}