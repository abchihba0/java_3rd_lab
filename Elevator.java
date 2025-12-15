import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Elevator extends Thread {

    private final int id;
    private final ElevatorType type;

    private int currentFloor = 1;
    private boolean busy = false;

    private final BlockingQueue<RideTask> tasks = new LinkedBlockingQueue<>();

    public Elevator(int id, ElevatorType type) {
        this.id = id;
        this.type = type;
    }


    public synchronized boolean isBusy() {
        return busy;
    }

    public int getCapacity() {
        return type.getCapacity();
    }

    public synchronized int getCurrentFloor() {
        return currentFloor;
    }

    public synchronized void assignTask(RideTask task) {
        tasks.add(task);
        busy = true;
    }

    @Override
    public void run() {
        try {
            while (true) {
                RideTask task = tasks.take();

                moveTo(task.fromFloor);
                openDoors();
                Logger.log("Лифт #" + id + " забрал " + task.passengers + " человек");

                moveTo(task.toFloor);
                openDoors();
                Logger.log("Лифт #" + id + " высадил пассажиров");

                synchronized (this) {
                    busy = false;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void moveTo(int floor) throws InterruptedException {
        Thread.sleep(Math.abs(currentFloor - floor) * 500L);
        currentFloor = floor;
        Logger.log("Лифт #" + id + " прибыл на этаж " + floor);
    }

    private void openDoors() throws InterruptedException {
        Logger.log("Лифт #" + id + " открывает двери");
        Thread.sleep(300);
        Logger.log("Лифт #" + id + " закрывает двери");
    }
}
