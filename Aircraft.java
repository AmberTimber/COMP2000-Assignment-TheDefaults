import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Aircraft extends Moveable implements drawable, Status, AircraftState {
    private final String aircraftID;
    private String operator;
    private String model;
    private double fuelLevel;
    private final int capacity;
    private boolean flying = false;
    private int countdown = 0;
    private int framesToConsumeFuel = 200;
    private int MaxframesToConsumeFuel = framesToConsumeFuel;
    private AirwayGate gateAssigned = null;
    private Color color = null; // lets each aircraft be told apart visually; null = subclass picks its own default
    private boolean canChangeState = false;

    // states
    private AircraftState currentState;
    private AircraftGroundedState groundedState;
    private AircraftFlyingState flyingState;
    private AircraftDockedState dockedState;

    // decorator
    public Status PlaneStatus;


    public Aircraft(String aircraftID, String operator, String model, double fuelLevel, int capacity) {
        
        this.aircraftID = aircraftID;
        setOperator(operator);
        setModel(model);
        setFuelLevel(fuelLevel);
        this.capacity = capacity;

        // states
        groundedState = new AircraftGroundedState(this);
        flyingState = new AircraftFlyingState(this);
        dockedState = new AircraftDockedState(this);
        setState(groundedState);
    }

    /**
     * Bounded generic range check shared by the aircraft setters.
     * Throws InvalidAircraftParameterException when value is outside [min, max].
     */
    protected static <T extends Comparable<T>> void requireInRange(T value, T min, T max, String field) {
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw new InvalidAircraftParameterException(
                field + " must be between " + min + " and " + max + " (was " + value + ")");
        }
    }

    //setters 
    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFuelLevel(double fuelLevel) {
        if (fuelLevel < 0) {
            throw new InvalidAircraftParameterException("Fuel level cannot be negative (was " + fuelLevel + ")");
        }
        this.fuelLevel = fuelLevel;
    }

    public void setFlying(boolean value) {
        flying = value;
    }

    public void setCountdown (int value) {
        countdown = value;
    }

    public void setGate(AirwayGate gateselected) {
        gateAssigned = gateselected;
    }

    public void setColor(Color color) {
        this.color = color;
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

    public boolean getFlying() {
        return flying;
    }

    public AirwayGate getAssignedGate() {
        return gateAssigned;
    }

    public Color getColor() {
        return color;
    }

    public boolean CooldownOver() {
        if (countdown <= 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public String getStatus() {
        return currentState.getStatus();
    }

    public String getAircraftType() {
        return "Generic aircraft";
    }

    // allows you to built up the status for future board
    public List<String> breakDownStatus(Status allstatus) {
        if (allstatus != null) {
        String text = allstatus.getOverallStatus();
        List<String> actualstatus = Arrays.stream(text.split(", ")).collect(Collectors.toList());
        return actualstatus;
        } else {
            System.out.println("error getting status because null");
            return null;
        }
    }

    // allows you to built up the status for plane text display
    @Override 
    public String getOverallStatus() {
        String message = getAircraftType() + ": " + getAircraftID();
        message += ", Operator: " + getOperator();
        message += ", Model: " + getModel();
        message += ", Fuel Level: " + getFuelLevel() + "L";
        message += ", Current status: " + getStatus();
        if (getState() == getGroundedState() || getState() == getDockedState()) {
            if (getCurrentNode() != null && getFlightPath() != null) {
                message += ", Current target is: " + getCurrentNode().getNodeID();
                message += ", Final destination is: " + getFlightPath().get(getFlightPath().size()-1).getNodeID();
            } else {
                message += ", Current target is: No Target";
            }
        }
        return message;
    }

    /*@Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawPlaneShape(drawer, width, height, getColor() != null ? getColor() : Color.YELLOW);
        drawer.setColor(Color.black);
        drawer.drawString(getOverallStatus(), getXPos() + 5, getYPos() - 20);
        drawer.drawString("Operator: " + this.getOperator(), getXPos() + 5, getYPos() - 5);
        drawer.drawString("Model: " + this.getModel(), getXPos() + 5, getYPos() + 10);
        drawer.drawString("Fuel Level: " + this.getFuelLevel() + "L", getXPos() + 5, getYPos() + 25);
        drawer.drawString("No. Passengers: " + capacity, getXPos() + 5, getYPos() + 40);
        drawer.drawString("Current status: " + this.getStatus(), getXPos() + 5, getYPos() + 55);
        drawer.drawString("Current target is: " + getCurrentNode().getNodeID(), getXPos() + 5, getYPos() + 70);
        drawer.drawString("Final destination is: " + getFlightPath().get(getFlightPath().size()-1).getNodeID(), getXPos() + 5, getYPos() + 85);
    }*/

    public void UpdateStats() {
        Status newStatus = this;
        PlaneStatus = newStatus;
    }

    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawPlaneShape(drawer, width, height, getColor() != null ? getColor() : Color.YELLOW);
        drawer.setColor(Color.black);
        List<String> text = breakDownStatus(PlaneStatus);
        for (int i = 0; i < text.size(); i++) {
            drawer.drawString(text.get(i), getXPos() + 10, (getYPos() - 20) + (i * 15));
        }
    }

    public void displayInfo() {
        System.out.println("Aircraft ID: " + aircraftID);
        System.out.println("Operator: " + operator);
        System.out.println("Model: " + model);
        System.out.println("Fuel Level: " + fuelLevel);
        System.out.println("Capacity: " + capacity);
        System.out.println("Status: " + getStatus());
    }

    public boolean canFly() {
        if (getReachedTarget() == true && getPosition().compareVectors(getFlightPath().get(getFlightPath().size()-1).getPosition()) == true) {
            Node lastNodeRef = getFlightPath().get(getFlightPath().size()-1);
            if (lastNodeRef.getNodeTileRepresentation() == Node.locationTypes.AIRFIELD) {
                return true;
            }
        }
        return false;
    }

    // used as a countdown before a plane leaves gate or flies off airfield
    public void decreaseCountdown() {
        if (countdown >= 0 && isAtGate() || countdown > 0 && getChosenToFly()) {
            countdown--;
        }
    }

    // allows plane to stay in on airfield for a second before flying away
    public void warmUpEngines() {
        if (countdown <= 0 && canFly()) {
            countdown = 200;
            System.out.println("Preparing for takeoff");
        }
    }

    // draws an airplane silhouette (nose, swept wings, tail wings) centred on this aircraft's
    // position and rotated to face its current heading; shared by both aircraft types.
    // shape points are in local space with the nose at "up" (0, -height/2), then the whole
    // thing is translated/rotated onto the canvas via an AffineTransform
    protected void drawPlaneShape(Graphics drawer, int width, int height, Color fillColor) {
        int[] xPoints = {
            0,
            scale(width, 0.08), scale(width, 0.5), scale(width, 0.12), scale(width, 0.28), scale(width, 0.06),
            0,
            -scale(width, 0.06), -scale(width, 0.28), -scale(width, 0.12), -scale(width, 0.5), -scale(width, 0.08)
        };
        int[] yPoints = {
            -scale(height, 0.5),
            -scale(height, 0.15), scale(height, 0.05), scale(height, 0.15), scale(height, 0.4), scale(height, 0.3),
            scale(height, 0.5),
            scale(height, 0.3), scale(height, 0.4), scale(height, 0.15), scale(height, 0.05), -scale(height, 0.15)
        };

        Graphics2D canvas = (Graphics2D) drawer;
        AffineTransform originalTransform = canvas.getTransform();

        canvas.translate(getXPos(), getYPos());
        // the shape's nose rests pointing "up" (angle -90deg), so rotate the extra amount
        // needed to bring it to the heading angle
        canvas.rotate(computeHeadingRadians() + Math.PI / 2);

        canvas.setColor(fillColor);
        canvas.fillPolygon(xPoints, yPoints, xPoints.length);
        canvas.setColor(Color.DARK_GRAY);
        canvas.drawPolygon(xPoints, yPoints, xPoints.length);

        canvas.setTransform(originalTransform);
    }

    // angle (radians) from this aircraft's current position to its target, in screen-coordinate
    // atan2 terms; falls back to pointing "up" when there's no meaningful direction yet
    private double computeHeadingRadians() {
        Vector2 target = getTarget();
        if (target == null || getCurrentNode() != null && getReachedTarget() && getCurrentNode().getNodeTileRepresentation() == Node.locationTypes.AIRFIELD || getState() == getDockedState()) {
            return Math.PI;
        }
        int dx = target.getXPos() - getXPos();
        int dy = target.getYPos() - getYPos();
        if (dx == 0 && dy == 0) {
            return -Math.PI / 2;
        }
        return Math.atan2(dy, dx);
    }

    private int scale(int dimension, double fraction) {
        return (int) Math.round(dimension * fraction);
    }

    //Consumes fuel
    public void influenceFuel() {
        if (!getStatus().equalsIgnoreCase("DOCKED") || getState() != dockedState) {
            if (framesToConsumeFuel <= 0) {
                fuelLevel--;
                framesToConsumeFuel = MaxframesToConsumeFuel;
            } else {
                framesToConsumeFuel--;
            }
        } else if (getStatus().equalsIgnoreCase("DOCKED") || getState() == dockedState) {
            if (framesToConsumeFuel <= MaxframesToConsumeFuel-10) {
                fuelLevel++;
                framesToConsumeFuel = MaxframesToConsumeFuel;
            } else {
                framesToConsumeFuel--;
            }
        }
    }

    // state works
    public void setState(AircraftState newState) {
        currentState = newState;
    }

    public AircraftState getState() {
        return currentState;
    }

    public AircraftGroundedState getGroundedState() {
        return groundedState;
    }

    public AircraftFlyingState getFlyingState() {
        return flyingState;
    }

    public AircraftDockedState getDockedState() {
        return dockedState;
    }

    // listens for runway changes
    public void RunwayNotice(boolean value) {
        canChangeState = value;
    }

    // state stuff
    @Override 
    public void Move(AirTrafficControl aircontroller) {
        UpdateStats();
        currentState.Move(aircontroller);
    }

    @Override 
    public void Dock() {

    }

    public void FlyNLand(AirTrafficControl aircontroller) {

    }
}