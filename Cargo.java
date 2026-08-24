public class Cargo {
    private double maxWeight; //in KG
    private double currentWeight;

    public Cargo (double maxWeight, double currentWeight) {
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
    }

    public double getmaxWeight() {
        return maxWeight;
    }

    public double getcurrentWeight() {
        return currentWeight;
    }
}