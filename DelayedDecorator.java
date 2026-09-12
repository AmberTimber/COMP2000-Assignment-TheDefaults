public class DelayedDecorator extends StatusDecorator {
    // this class exists to help the status decorator to add delayed
    public DelayedDecorator(Status currentStatus) {
        super(currentStatus);
    }

    @Override // adds the delayed status
    public String getOverallStatus() {
        return currentStatus.getOverallStatus() + ", is currently delayed";
    }
}
