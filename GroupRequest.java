public class GroupRequest {
    public final int fromFloor;
    public final int toFloor;
    public int remainingPassengers;

    public GroupRequest(int fromFloor, int toFloor, int passengers) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.remainingPassengers = passengers;
    }
}
