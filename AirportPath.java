public abstract class AirportPath{
    private final String pathID;
    private final double lengthInMeters;
    
    public AirportPath (String pathID, double lengthInMeters){
        this. pathID = pathID;
        this.lengthInMeters = lengthInMeters;
    }

    /*
    AirportPath class don't need setters because pathID and path length will never change its values.
    */

    public String getPathID(){
        return pathID;
    }

    public double getLengthInMeters(){
        return lengthInMeters;
    }
}
