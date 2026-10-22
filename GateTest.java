
public class GateTest {
    public static void main(String[] args) {
        Node gateNode = new Node(null, null, null, null,
                new Vector2(100, 200), "N1", Node.locationTypes.GATE);

        AirwayGate gate = new AirwayGate("G12", true, gateNode);

        CommercialPlane plane = new CommercialPlane(
                "QF101", "Qantas", "Airbus A320", 82.5, 180, "Grounded", 180, 145);
        CommercialPlane plane2 = new CommercialPlane(
                "QF202", "Qantas", "Airbus A321", 75.0, 200, "Grounded", 200, 150);

        System.out.println("Gate free before parking: " + gate.isFree());

        try {
            gate.parkPlane(plane);
            System.out.println("Parked " + plane.getAircraftID());
        } catch (OccupancyException e) {
            System.out.println("Unexpected: " + e.getMessage());
        }

        System.out.println("Gate free after parking: " + gate.isFree());

        try {
            gate.parkPlane(plane2); // should fail: double-park
        } catch (OccupancyException e) {
            System.out.println("Expected error: " + e.getMessage());
        }

        gate.setStatus(false); // close the gate
        System.out.println("Gate free when closed: " + gate.isFree()); // should be false now

        gate.removePlane();
        gate.displayInfo();
    }
}