public class CargoPlane extends Aircraft {
    private double maxWeight; //in KG
    private double currentWeight;

    public void SetCargo (double maxWeight, double currentWeight) {
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
    }
    
    // getters
    public double getmaxWeight() {
        return maxWeight;
    }

    public double getcurrentWeight() {
        return currentWeight;
    }

    //setters
    public void setmaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setcurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }
}