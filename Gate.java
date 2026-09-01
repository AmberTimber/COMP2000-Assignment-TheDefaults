import java.util.ArrayList;

public class Gate extends Vector2 {
    private Aircraft assignAircraft = null;
    private int gateNumber;
    private static ArrayList<Integer> allGateNumbers = new ArrayList<Integer>();

    public Gate (int gateNumber) { // sets gate number
        if (!allGateNumbers.isEmpty() && allGateNumbers != null) {
            int containsNumb = 0;
            for (int i = 0; i < allGateNumbers.size(); i++) {
                if (gateNumber == allGateNumbers.get(i)) {
                    containsNumb++;
                }
            }
            if (containsNumb <= 0) { // ensure number is Unique
                this.gateNumber = gateNumber;
                allGateNumbers.add(gateNumber);
            } else {
                System.out.println("Why is the gate number not Unique?");
            }
        }
        
    }

    public int getGateNumber () {
        return gateNumber;
    }

    public void setnAircraft(Aircraft assignedAircraft) { // allows aircraft to move towards gate
        this.assignAircraft = assignedAircraft;
    }

    public boolean dockedAircraft () { // used to detect if a gate has a aircraft or not
        if (assignAircraft != null) {
            return true;
        } else {
            return false;
        }
    }
}
