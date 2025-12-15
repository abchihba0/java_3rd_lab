import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Ввод количества этажей
        int floors;
        while (true) {
            System.out.print("Сколько этажей в доме: ");
            try {
                floors = Integer.parseInt(sc.nextLine());
                if (floors > 1) break;
                System.out.println("Количество этажей должно быть больше 1");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число");
            }
        }

        // Ввод количества лифтов
        int elevatorsCount;
        while (true) {
            System.out.print("Сколько лифтов создать: ");
            try {
                elevatorsCount = Integer.parseInt(sc.nextLine());
                if (elevatorsCount > 0) break;
                System.out.println("Количество лифтов должно быть больше 0");
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число");
            }
        }

        // Вывод типов лифтов и их вместимость
        System.out.println();
        System.out.println("Типы лифтов:");
        System.out.println("1 — пассажирский (вместимость "
                + ElevatorType.PASSENGER.getCapacity() + " человек)");
        System.out.println("2 — грузовой (вместимость "
                + ElevatorType.FREIGHT.getCapacity() + " человек)");
        System.out.println();

        List<Elevator> elevators = new ArrayList<>();

        // Создание лифтов
        for (int i = 0; i < elevatorsCount; i++) {
            int typeInput;
            while (true) {
                System.out.print("Тип лифта " + (i + 1) + ": ");
                try {
                    typeInput = Integer.parseInt(sc.nextLine());
                    if (typeInput == 1 || typeInput == 2) break;
                    System.out.println("Введите 1 или 2");
                } catch (NumberFormatException e) {
                    System.out.println("Введите целое число");
                }
            }

            ElevatorType type = (typeInput == 2)
                    ? ElevatorType.FREIGHT
                    : ElevatorType.PASSENGER;

            Elevator elevator = new Elevator(i + 1, type);
            elevators.add(elevator);
            elevator.start();
        }

        // Ввод запросов
        System.out.println();
        System.out.println("Введите запросы в формате:");
        System.out.println("<откуда> <куда> <количество_людей>");
        System.out.println("Можно вводить несколько запросов подряд, через пробел или новую строку");
        System.out.println("Для выхода напишите: exit");
        System.out.println();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("exit")) {
                System.out.println("Завершение ввода запросов");
                break;
            }

            if (line.isEmpty()) continue;

            String[] tokens = line.split("\\s+");
            if (tokens.length % 3 != 0) {
                System.out.println("Некорректный формат. Пример: 1 10 50");
                continue;
            }

            for (int i = 0; i < tokens.length; i += 3) {
                try {
                    int from = Integer.parseInt(tokens[i]);
                    int to = Integer.parseInt(tokens[i + 1]);
                    int people = Integer.parseInt(tokens[i + 2]);

                    if (from < 1 || to < 1 || from > floors || to > floors) {
                        System.out.println("Этажи должны быть в диапазоне 1.." + floors);
                        continue;
                    }

                    if (from == to || people <= 0) {
                        System.out.println("Некорректные значения");
                        continue;
                    }

                    GroupRequest request = new GroupRequest(from, to, people);
                    Dispatcher dispatcher = new Dispatcher(elevators, request);
                    dispatcher.start();

                    Logger.log("Принят запрос: "
                            + from + " -> " + to
                            + ", людей: " + people);

                } catch (NumberFormatException e) {
                    System.out.println("Ошибка ввода чисел");
                }
            }
        }
    }
}
