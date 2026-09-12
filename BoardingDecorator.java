public class BoardingDecorator extends StatusDecorator {
    // used to add boarding modifier to the status abstract class
    public BoardingDecorator(Status currentStatus) {
        super(currentStatus);
    }

    @Override // adds the boarding modifier
    public String getOverallStatus() {
        return currentStatus.getOverallStatus() + ", is currently Boarding";
    }
}
