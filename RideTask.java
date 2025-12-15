public class RideTask {
    public final int fromFloor;
    public final int toFloor;
    public final int passengers;

    public RideTask(int fromFloor, int toFloor, int passengers) {
        this.fromFloor = fromFloor;
        this.toFloor = toFloor;
        this.passengers = passengers;
    }
}