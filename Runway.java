public class Runway extends AirportPath{
    private int capacity;
    private String status;
    private Aircraft currentPlane; //current plane at the runway

    public Runway(String pathID, double lengthInMeters, double runwayID, int capacity, Aircraft currentPlane, String status){
        super(pathID, lengthInMeters);
        this.capacity = capacity;
        this.status = status;
        this.currentPlane = currentPlane;
    }

    //setters
    public void setStatus(String status){
        this.status = status;
    }

    //getters
    public int getCapacity(){
        return capacity;
    }

    public Aircraft getCurrentPlane(){
        return currentPlane; 
    }

    public String getStatus(){
        System.out.println("Status: " + currentPlane + "is boarding.");
        return status;
    }

    public boolean canAcceptPlane(Aircraft plane){
        if (currentPlane == null){
            return true;
        }
        return false; 
    }

    public boolean enterRunway(Aircraft plane){
        if (canAcceptPlane(plane)){
            currentPlane = plane;
            return true;
        }
        return false;
    }

}