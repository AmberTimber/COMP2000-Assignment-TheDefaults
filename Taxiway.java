import java.util.ArrayList;
import java.util.List;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Taxiway extends AirportPath {

  private final int capacity;
  private final double maxSpeedKnots;
  private final List<Aircraft> aircraftOnTaxiway = new ArrayList<>(); // aircraft currently occupying the taxiway

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
   * Checks whether the taxiway has room for another aircraft.
   * Returns false if the taxiway is already at capacity, the aircraft is
   * null, or the aircraft is already on the taxiway.
   *
   * Implemented by: Kyle
   */
  public boolean canAcceptAircraft(Aircraft aircraft) {
    if (aircraft == null) {
      return false;
    }
    if (aircraftOnTaxiway.contains(aircraft)) {
      return false;
    }
    return getCurrentOccupancy() < capacity;
  }

  /**
   * Adds an aircraft to the taxiway if space allows.
   * Throws OccupancyException if the taxiway is already at full capacity.
   *
   * Implemented by: Kyle
   */
  public boolean enterTaxiway(Aircraft aircraft) throws OccupancyException {
    if (!canAcceptAircraft(aircraft)) {
      throw new OccupancyException(
          "Taxiway " + getPathID() + " is at full capacity ("
          + capacity + "/" + capacity + "). Cannot accept aircraft "
          + (aircraft != null ? aircraft.getAircraftID() : "null") + ".");
    }
    aircraftOnTaxiway.add(aircraft);
    return true;
  }

  /**
   * Removes and returns the next aircraft ready to leave the taxiway
   * (first-in, first-out), or null if the taxiway is empty.
   */
  public Aircraft exitTaxiWay() {
    if (aircraftOnTaxiway.isEmpty()) {
      return null;
    }
    return aircraftOnTaxiway.remove(0);
  }

  // Implemented by: Kyle
  public int getCurrentOccupancy() {
    return aircraftOnTaxiway.size();
  }

  public int getCapacity() {
    return capacity;
  }

  public double getMaxSpeedKnots() {
    return maxSpeedKnots;
  }

  /**
   * Returns the IDs of all aircraft currently on the taxiway, in the order they entered.
   *
   * Implemented by: Kyle
   */
  public List<String> getAircraftIDs() {
    return aircraftOnTaxiway.stream()
        .map(aircraft -> aircraft.getAircraftID())
        .collect(Collectors.toList());
  }

  /**
   * Returns each operator on the taxiway once, with duplicates removed.
   *
   * Implemented by: Kyle
   */
  public Set<String> getOperators() {
    return aircraftOnTaxiway.stream()
        .map(Aircraft::getOperator)
        .collect(Collectors.toSet());
  }

  /**
   * Maps each aircraft ID to its aircraft for quick lookup.
   * If two aircraft share an ID, the first one to enter is kept.
   *
   * Implemented by: Kyle
   */
  public Map<String, Aircraft> getAircraftByID() {
    return aircraftOnTaxiway.stream()
        .collect(Collectors.toMap(
            Aircraft::getAircraftID,       // key
            aircraft -> aircraft,          // value
            (first, second) -> first));    // merge rule for duplicate keys
  }

  /**
   * Groups aircraft on the taxiway by type (CargoPlane / CommercialPlane).
   *
   * Implemented by: Kyle
   */
  public Map<String, List<Aircraft>> groupByType() {
    return aircraftOnTaxiway.stream()
        .collect(Collectors.groupingBy(aircraft -> aircraft.getClass().getSimpleName()));
  }

  /**
   * Groups aircraft by operator, then by type within each operator.
   * e.g. Qantas -> {CommercialPlane=[...], CargoPlane=[...]}
   *
   * Implemented by: Kyle
   */
  public Map<String, Map<String, List<Aircraft>>> groupByOperatorThenType() {
    return aircraftOnTaxiway.stream()
        .collect(Collectors.groupingBy(
            Aircraft::getOperator,
            Collectors.groupingBy(aircraft -> aircraft.getClass().getSimpleName())));
  }

  /**
   * Fuel statistics (count, min, max, average, sum) for aircraft on the taxiway.
   *
   * Implemented by: Kyle
   */
  public DoubleSummaryStatistics getFuelStatistics() {
    return aircraftOnTaxiway.stream()
        .collect(Collectors.summarizingDouble(Aircraft::getFuelLevel));
  }

  /**
   * Capacity statistics (count, min, max, average, sum) for aircraft on the taxiway.
   *
   * Implemented by: Kyle
   */
  public IntSummaryStatistics getCapacityStatistics() {
    return aircraftOnTaxiway.stream()
        .collect(Collectors.summarizingInt(Aircraft::getCapacity));
  }

  /**
   * Average capacity of aircraft on the taxiway (0.0 if empty).
   *
   * Implemented by: Kyle
   */
  public double getAverageCapacity() {
    return aircraftOnTaxiway.stream()
        .collect(Collectors.averagingInt(Aircraft::getCapacity));
  }

  /**
   * Builds a text manifest of the taxiway using a custom collector.
   *
   * Implemented by: Kyle
   */
  public String getManifest() {
    Collector<Aircraft, StringBuilder, String> manifestCollector = Collector.of(
        StringBuilder::new,                                   // supplier: empty container
        (builder, aircraft) -> builder.append("  - ")         // accumulator: add one aircraft
            .append(aircraft.getAircraftID())
            .append(" (").append(aircraft.getOperator()).append(")\n"),
        (left, right) -> left.append(right),                  // combiner: merge two builders
        builder -> "Taxiway " + getPathID() + " ["            // finisher: final String
            + getCurrentOccupancy() + "/" + capacity + "]\n" + builder);

    return aircraftOnTaxiway.stream().collect(manifestCollector);
  }
}
