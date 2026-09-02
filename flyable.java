public interface  flyable {
    boolean flying();
    boolean isReadyForLanding();
    boolean isReadyForTakeoff();
    Vector2 getDestinationPostion();
    
    void setLocation(Vector2 newPos);
}
