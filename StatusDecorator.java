public abstract class StatusDecorator implements Status {
    // this class is part of status.java class, and is used to decorate
    //  the text, so you can add more types of text into the future information board if needed
    protected Status currentStatus;

    public StatusDecorator (Status setStatus) {
        this.currentStatus = setStatus;
    }

    @Override // gets the given statuses status
    public String getOverallStatus() {
        return currentStatus.getOverallStatus();
    }
}
