public enum ElevatorType {
    PASSENGER(7),
    FREIGHT(15);

    private final int capacity;

    ElevatorType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
