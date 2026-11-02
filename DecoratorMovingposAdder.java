public class DecoratorMovingposAdder extends StatusDecorator {
    // used to add moving location modifier to the status abstract class
    public DecoratorMovingposAdder(Status currentStatus) {
        super(currentStatus);
    }

    @Override // adds the boarding modifier
    public String getOverallStatus() {
        return currentStatus.getOverallStatus() + ", is currently Boarding";
    }
}
