/**
 * 
 * ABSTRACT CLASS: AirportPath
 * 
 * Why this class exists:
 * Both Runway and Taxiway represent physical ground paths that planes travel
 * across,
 * sharing attributes like length, unique IDs, and availability status.
 * 
 * It is Abstract to prevent direct instantiation - AirportPath is a generic
 * concept
 */
public abstract class AirportPath {
  private String id;
  private double lengthInMeters;

  // Constructor(called by child classes using super())
  public AirportPath(String id, double lengthInMeters) {
    this.id = id;
    this.lengthInMeters = lengthInMeters;
  }

  public String getId() {
    return id;
  }

  // Child class needs to override with appropriate return instance
  public abstract boolean canAcceptAircraft(Aircraft aircraft);
}
