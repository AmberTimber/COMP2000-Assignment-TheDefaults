import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Aircraft extends Moveable implements drawable, Status, AircraftState, vehicle {
    private final String aircraftID;
    private String operator;
    private String model;
    private double fuelLevel;
    private final int capacity;
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
        String message = getVehicleType() + ": " + getAircraftID();
        message += ", Operator: " + getOperator();
        message += ", Model: " + getModel();
        message += ", Fuel Level: " + getFuelLevel() + "L";
        message += ", Current status: " + getStatus();
        message += getCargoSummary();
        if (getState() == getGroundedState() || getState() == getDockedState()) {
            if (getCurrentNode() != null && getFlightPath() != null) {
                message += ", Current target is: " + getCurrentNode().getNodeID();
                message += ", Final destination is: " + getFlightPath().get(getFlightPath().size()-1).getNodeID();
            } else {
                message += ", Current target is: No Target";
            }
        }
        if (isBlocked() == true) {
            message += ", Currently being blocked";
        }
        return message;
    }

    // Updates decorator pattern text with new status
    public void UpdateStats() {
        Status newStatus = this;
        PlaneStatus = newStatus;
    }

    @Override
    public void visualRepresentation(Graphics drawer, int width, int height) {
        drawPlaneShape(drawer, width, height, getColor() != null ? getColor() : Color.YELLOW);
        // status lines in the pixel font, light text with a dark shadow so it reads on any ground
        List<String> text = breakDownStatus(PlaneStatus);
        for (int i = 0; i < text.size(); i++) {
            PixelArt.shadowText(drawer, text.get(i).toUpperCase(), getXPos() + 10, (getYPos() - 30) + (i * 15), 2,
                    new Color(245, 245, 235), new Color(20, 20, 26));
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

    // draws a detailed airplane (wings, tailplane, engines, fuselage, cockpit, lights) centred on
    // this aircraft's position and rotated to face its current heading; shared by both aircraft types.
    // everything is drawn in local space with the nose at "up" (0, -height/2), then the whole
    // thing is translated/rotated into place
    //
    // pixel-art style: the plane is drawn at 1/PLANE_PIXEL resolution into a small sprite with
    // antialiasing off (so colours stay flat), then scaled back up with nearest-neighbour so every
    // "pixel" is a crisp square block on screen no matter which way the plane is facing
    private static final int PLANE_PIXEL = 2;

    protected void drawPlaneShape(Graphics drawer, int width, int height, Color fillColor) {
        int cells = (int) (Math.max(width, height) * 1.6) / PLANE_PIXEL; // room for rotation + shadow
        int size = cells * PLANE_PIXEL;
        BufferedImage sprite = new BufferedImage(cells, cells, BufferedImage.TYPE_INT_ARGB);
        Graphics2D canvas = sprite.createGraphics();
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        canvas.scale(1.0 / PLANE_PIXEL, 1.0 / PLANE_PIXEL);
        canvas.translate(size / 2.0, size / 2.0);
        // the shape's nose rests pointing "up" (angle -90deg), so rotate the extra amount
        // needed to bring it to the heading angle
        canvas.rotate(computeHeadingRadians() + Math.PI / 2);

        Color wingColor = fillColor.darker();
        Color outline = new Color(40, 40, 40);
        canvas.setStroke(new BasicStroke(1f));

        // drop shadow while airborne so flying planes look like they're above the ground
        if (getState() == getFlyingState()) {
            canvas.setColor(new Color(0, 0, 0, 60));
            canvas.fill(new Ellipse2D.Double(-width * 0.35 + 6, -height * 0.2 + 8, width * 0.7, height * 0.5));
        }

        // main wings (swept back)
        Path2D wings = new Path2D.Double();
        wings.moveTo(-width * 0.06, -height * 0.12);
        wings.lineTo(-width * 0.5, height * 0.08);
        wings.lineTo(-width * 0.5, height * 0.15);
        wings.lineTo(-width * 0.06, height * 0.08);
        wings.lineTo(width * 0.06, height * 0.08);
        wings.lineTo(width * 0.5, height * 0.15);
        wings.lineTo(width * 0.5, height * 0.08);
        wings.lineTo(width * 0.06, -height * 0.12);
        wings.closePath();
        fillAndOutline(canvas, wings, wingColor, outline);

        // engines slung under each wing
        drawEngines(canvas, width, height, outline);

        // tailplane (horizontal stabiliser)
        Path2D tail = new Path2D.Double();
        tail.moveTo(-width * 0.04, height * 0.32);
        tail.lineTo(-width * 0.22, height * 0.44);
        tail.lineTo(-width * 0.22, height * 0.48);
        tail.lineTo(width * 0.22, height * 0.48);
        tail.lineTo(width * 0.22, height * 0.44);
        tail.lineTo(width * 0.04, height * 0.32);
        tail.closePath();
        fillAndOutline(canvas, tail, wingColor, outline);

        // fuselage: long rounded body from nose to tail
        Shape fuselage = getFuselageShape(width, height);
        fillAndOutline(canvas, fuselage, fillColor, outline);

        // vertical fin seen from above, a thin dark strip along the tail
        canvas.setColor(wingColor.darker());
        canvas.fill(new RoundRectangle2D.Double(-width * 0.015, height * 0.28, width * 0.03, height * 0.22, 2, 2));

        // subclass-specific markings (passenger windows, cargo doors, etc.)
        drawTypeDetails(canvas, width, height);

        // cockpit windscreen
        canvas.setColor(new Color(30, 50, 80));
        canvas.fill(new Ellipse2D.Double(-width * 0.045, -height * 0.42, width * 0.09, height * 0.07));
        canvas.setColor(new Color(150, 200, 255, 160));
        canvas.fill(new Ellipse2D.Double(-width * 0.02, -height * 0.415, width * 0.03, height * 0.03));

        // navigation lights: red on the left wingtip, green on the right, white on the tail
        double lightSize = Math.max(3, width * 0.06);
        canvas.setColor(Color.RED);
        canvas.fill(new Ellipse2D.Double(-width * 0.5 - lightSize / 2, height * 0.115 - lightSize / 2, lightSize, lightSize));
        canvas.setColor(new Color(0, 220, 0));
        canvas.fill(new Ellipse2D.Double(width * 0.5 - lightSize / 2, height * 0.115 - lightSize / 2, lightSize, lightSize));
        canvas.setColor(Color.WHITE);
        canvas.fill(new Ellipse2D.Double(-lightSize / 3, height * 0.5 - lightSize / 3, lightSize / 1.5, lightSize / 1.5));

        canvas.dispose();

        // blow the sprite back up to full size without smoothing
        Graphics2D screen = (Graphics2D) drawer;
        Object originalInterpolation = screen.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
        screen.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        screen.drawImage(sprite, getXPos() - size / 2, getYPos() - size / 2, size, size, null);
        if (originalInterpolation != null) {
            screen.setRenderingHint(RenderingHints.KEY_INTERPOLATION, originalInterpolation);
        }
    }

    // body of the plane; subclasses can override to make it wider/narrower
    protected Shape getFuselageShape(int width, int height) {
        return new RoundRectangle2D.Double(-width * 0.07, -height * 0.5, width * 0.14, height, width * 0.14, height * 0.25);
    }

    // one engine under each wing by default; subclasses can override for more
    protected void drawEngines(Graphics2D canvas, int width, int height, Color outline) {
        drawEngine(canvas, -width * 0.22, -height * 0.02, width, height, outline);
        drawEngine(canvas, width * 0.22, -height * 0.02, width, height, outline);
    }

    // a single engine pod centred at (cx, cy) in local space
    protected void drawEngine(Graphics2D canvas, double cx, double cy, int width, int height, Color outline) {
        double engineW = width * 0.07;
        double engineH = height * 0.16;
        Shape pod = new RoundRectangle2D.Double(cx - engineW / 2, cy - engineH / 2, engineW, engineH, engineW, engineW);
        fillAndOutline(canvas, pod, new Color(90, 90, 95), outline);
        // dark intake at the front of the engine
        canvas.setColor(Color.BLACK);
        canvas.fill(new Ellipse2D.Double(cx - engineW * 0.35, cy - engineH / 2, engineW * 0.7, engineW * 0.5));
    }

    // hook for subclasses to add their own markings on top of the fuselage
    protected void drawTypeDetails(Graphics2D canvas, int width, int height) {
    }

    protected void fillAndOutline(Graphics2D canvas, Shape shape, Color fill, Color outline) {
        canvas.setColor(fill);
        canvas.fill(shape);
        canvas.setColor(outline);
        canvas.draw(shape);
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

    //Consumes fuel
    @Override 
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

    @Override 
    public void swapOperators(String newOp) {

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

    public Boolean canChangeStates() {
        return canChangeState;
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

    @Override 
    public void FlyNLand(AirTrafficControl aircontroller) {

    }
}