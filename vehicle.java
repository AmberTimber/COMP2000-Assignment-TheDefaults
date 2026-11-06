public interface vehicle {
    // this ensures vehicles must interact with cargo
    public void influenceFuel();
    public void influenceCargo(boolean reStocking);
    public void swapOperators(String newOp);
    public String getVehicleType();
    public String getCargoSummary();
    public int getMaxCapacity();
    public int getCurrentCapacity();
}
