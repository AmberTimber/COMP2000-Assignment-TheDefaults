public class CommercialPlane extends Aircraft {
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

}