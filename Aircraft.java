public abstract class Aircraft {
    private final String aircraftID;
    private String operator;
    private String model;
    private double fuelLevel;
    private final int capacity;
    private String status;
    private Moveable aircraftPosition = new Moveable();


    public Aircraft(String aircraftID, String operator, String model, double fuelLevel, int capacity, String status) {
        this.aircraftID = aircraftID;
        setOperator(operator);
        setModel(model);
        setFuelLevel(fuelLevel);
        this.capacity = capacity;
        this.status = "GROUNDED"; // Default status of an Aircraft
    }

    //setters 
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFuelLevel(double fuelLevel) {
        if (fuelLevel >= 0) {
            this.fuelLevel = fuelLevel;
        }
    }

    public Moveable setAircraftStuff() { // related to moving
        return aircraftPosition;
    }

    //getters
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
        if(status.equals("GOUNDED")){
            System.out.println("Status: " + aircraftID + " is grounded.");
        } else if (status.equals("BOARDING")){
            System.out.println("Status: " + aircraftID + " is boarding.");
        } else if (status.equals("IN-FLIGHT")){
            System.out.println("Status: " + aircraftID + " is in-flight.");
        }
        return status;
    }

    public void displayInfo() {
        System.out.println("Aircraft ID: " + aircraftID);
        System.out.println("Operator: " + operator);
        System.out.println("Model: " + model);
        System.out.println("Fuel Level: " + fuelLevel);
        System.out.println("Capacity: " + capacity);
        System.out.println(getStatus());
    }
}
