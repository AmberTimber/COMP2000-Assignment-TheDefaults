import java.util.ArrayList;

public class Airport {
    private String AirportName;
    private ArrayList<AirportPath> airportPaths;
    private ArrayList<AirwayGate> airwayGates;
    private ArrayList<Taxiway> taxiways;
    private ArrayList<CargoPlane> cargoPlanes;
    private ArrayList<CommercialPlane> commercialPlanes;
    private ArrayList<Aircraft> aircrafts;
    private ArrayList<Runway> runways;
    private ArrayList<Moveable> directions;

    public Airport(String airportName) {
        this.AirportName = airportName;
        this.airportPaths = new ArrayList<>();
        this.airwayGates = new ArrayList<>();
        this.taxiways = new ArrayList<>();
        this.cargoPlanes = new ArrayList<>();
        this.commercialPlanes = new ArrayList<>();
        this.aircrafts = new ArrayList<>();
        this.runways = new ArrayList<>();
        this.directions = new ArrayList<>();
    } 
}