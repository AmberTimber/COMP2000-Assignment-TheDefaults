public class Aircraft {
    private String aircraftID;
    private String operator;
    private String model;
    private double fuelLevel;
    private int capacity;
    private String status;

    public Aircraft(String aircraftID, String operator, String model, double fuelLevel, int capacity, String status) {
        this.aircraftID = aircraftID;
        this.operator = operator;
        this.model = model;
        this.fuelLevel = fuelLevel;
        this.capacity = capacity;
        this.status = "GROUNDED"; // Default status of an Aircraft
    }

    public String getAircraftID() {
        return aircraftID;
    }

    public String getOperator() {
        return operator;
    }

    public String getModel() {
        return model;
    }

    public double getFuelLevel() {
        return fuelLevel;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public void displayInfo() {
        System.out.println("Aircarft-ID: " + getAircraftID());
        System.out.println("Airlines: " + getairlines());
        System.out.println("Model: " + getModel());
        System.out.println("Current Status: " + getStatus());
        System.out.println("Passenger-Capacity: " + getCapacity());
    }
}
