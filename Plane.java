public class Plane {
    private String planeID;
    private String model;
    private String destination;
    private boolean bording; 
    private int capacity;

    public Plane (String planeID, String model, String destination, boolean bording, int capacity){
        this.planeID = planeID;
        this.model = model;
        this.destination = destination;
        this.bording = bording;
        this.capacity = capacity;
    }

    public String getPlaneID(){
        return planeID;
    }

    public String getModel(){
        return model;
    }

    public String getDestination(){
        return destination;
    }

    public boolean getBording(){
        return bording;
    }

    public int getCapacity(){
        return capacity;
    }

    public void displayInfo(){
        System.out.println("Plane ID: " + planeID);
        System.out.println("Model: " + model);
        System.out.println("Destination: " + destination);
        System.out.println("Bording: " + bording);
        System.out.println("Capacity: " + capacity);
    }
}
