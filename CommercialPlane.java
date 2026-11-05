

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
}