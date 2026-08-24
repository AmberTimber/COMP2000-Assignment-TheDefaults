public class Taxiway extends AirportPath {

  private final int capacity;
  private final double maxSpeedKnots;

  /**
   * 
   * @param id:             Taxiway id
   * @param lengthInMeters: Physical length of the taxiway
   * @param capacity:       Maximum number of aircraft allowed on the taxiway
   *                        simultaneously
   * @param maxSpeedKnots:  Ground speed limit for aircraft transit
   */
  public Taxiway(String id, double lengthInMeters, int capacity, double maxSpeedKnots) {
    super(id, lengthInMeters);
    this.capacity = capacity;
    this.maxSpeedKnots = maxSpeedKnots;
  }

  /**
   * Overridden from AirportPath abstract class.
   * "Come up with logic"
   */
  @Override
  public boolean canAcceptAircraft(Aircraft aircraft) {
    return false; // needs to be modified (come up with logic)
  }

  /**
   * 
   * Adds an aircraft to the taxiway is space allows
   */
  public boolean enterTaxiway(Aircraft aircraft) {
    if (canAcceptAircraft(aircraft)) {
      // logic
      return true;
    }
    return false;
  }

  public Aircraft exitTaxiWay() {
    // return
  }

  // change return val
  public int getCurrentOccupancy() {
    return 0;
  }

  public int getCapacity() {
    return capacity;
  }

  public double getMaxSpeedKnots() {
    return maxSpeedKnots;
  }
}
