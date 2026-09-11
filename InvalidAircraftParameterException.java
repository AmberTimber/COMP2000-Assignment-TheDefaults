/**
 * Thrown when an aircraft field is set to a value outside its valid range
 * (negative fuel, passengers above seat count, cargo above max weight, ...).
 * 
 * 
 * Unchecked on purpose: passing an out-of-range value is a caller-side
 * precondition violation, not a recoverable runtime condition. Making it
 * checked would force `throws` through the Aircraft/CargoPlane constructors
 * and every call site. Checked-exception handling in this project is covered
 * by OccupancyException.
 */
public class InvalidAircraftParameterException extends IllegalArgumentException {
    public InvalidAircraftParameterException(String message) {
        super(message);
    }
}
