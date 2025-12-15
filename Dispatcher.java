import java.util.Comparator;
import java.util.List;

public class Dispatcher extends Thread {

    private final List<Elevator> elevators;
    private final GroupRequest request;

    public Dispatcher(List<Elevator> elevators, GroupRequest request) {
        this.elevators = elevators;
        this.request = request;
    }

    @Override
    public void run() {
        Logger.log("Запрос: " + request.remainingPassengers + " человек");

        while (request.remainingPassengers > 0) {

            elevators.stream()
                    .filter(e -> !e.isBusy())
                    .sorted(Comparator.comparingInt(
                            e -> Math.abs(e.getCurrentFloor() - request.fromFloor)
                    ))
                    .forEach(elevator -> {
                        if (request.remainingPassengers <= 0) return;

                        int take = Math.min(
                                elevator.getCapacity(),
                                request.remainingPassengers
                        );

                        request.remainingPassengers -= take;

                        elevator.assignTask(
                                new RideTask(request.fromFloor, request.toFloor, take)
                        );

                        Logger.log(
                                "Назначен лифт, везёт " + take +
                                ", осталось " + request.remainingPassengers
                        );
                    });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Logger.log("Все пассажиры доставлены");
    }
}
