package laba3;
/*
Путь к журналу 1 - из файла
Способ реализации собитий - 0 - явная реализация
000001011 - код
События:
1. Обращение к потоку вывода на консоль
2. Обращение к указанному массиву
4. Обращение к потоку ввода с консоли
*/


import java.io.*;
import java.util.*;

//Интерфейс события
interface IEventListener {
    void onEvent(String message);
}

//Класс источника событий
class EventSource {
    private IEventListener listener;

    public EventSource(IEventListener listener) {
        this.listener = listener;
    }

    public void generateEvent(String message) {
        listener.onEvent(message);
    }
}

class ConsoleLogger implements IEventListener {
    @Override
    public void onEvent(String message) {
        System.out.println(message); // Вывод на консоль
    }
}

class FileLogger implements IEventListener {
    private String filePath;

    public FileLogger(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void onEvent(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл.");
        }
    }
}

public class Laba3 {

    public static void logEventToAllSources(EventSource[] sources, String message) {
        for (EventSource source : sources) {
            source.generateEvent(message);
        }
    }

    public static void main(String[] args) {

        ConsoleLogger consoleLogger = new ConsoleLogger();
        FileLogger fileLogger;

        EventSource consoleSource = new EventSource(consoleLogger);
        EventSource fileSource;
        EventSource[] eventSources;

        // Получаем путь к файлу из консоли
        Scanner scanner = new Scanner(System.in);

        String logBeforeOpenLoggerFile = "Обращение к потоку вывода на консоль с запросом ввода пути к файлу с данными.";
        consoleSource.generateEvent(logBeforeOpenLoggerFile);

        consoleSource.generateEvent("Обращение к потку ввода с консоли для получения пути к файлу с данными.");

        System.out.print("Введите путь к файлу с данными: ");
        String filePath = scanner.nextLine();
        consoleSource.generateEvent("Была введена строка: "  + filePath);
        logBeforeOpenLoggerFile += "\nОбращение к потку ввода с консоли для получения пути к файлу с данными. Была введена строка: "  + filePath;

        String logFilePath;

        List<Integer> numbers = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            try {

                // Считываем путь к файлу журнала из первой строки
                String line = br.readLine();
                if (line != null) {
                    logFilePath = line.trim();
                } else {
                    throw new RuntimeException("Файл пуст.");
                }

                fileLogger = new FileLogger(logFilePath);
                fileSource = new EventSource(fileLogger);
                eventSources = new EventSource[]{consoleSource, fileSource};


                logEventToAllSources(eventSources, logBeforeOpenLoggerFile);

                //Note: Либо логаем с помощью функции, передавая туда приемники, либо поодиночке

                /*
                consoleSource.generateEvent(logBeforeOpenLoggerFile);
                fileSource.generateEvent(logBeforeOpenLoggerFile);
                 */

                logEventToAllSources(eventSources, "Обращение к указанному массиву: считывание из файла");

                // Считываем вторую строку, содержащую числа
                line = br.readLine();
                if (line != null) {
                    String[] items = line.split("\\s+");
                    for (String token : items) {
                        try {
                            numbers.add(Integer.parseInt(token.trim()));
                        } catch (NumberFormatException e) {
                            logEventToAllSources(eventSources,"Пропущен нецелочисленный элемент: " + token);
                        }
                    }
                }
            } finally {
                br.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logEventToAllSources(eventSources, "Обращение к указанному массиву: вычисление сумм.");

        int evenNegativeSum = 0;
        int oddNegativeSum = 0;

        for (int num : numbers) {
            if (num < 0) {
                if (num % 2 == 0) {
                    evenNegativeSum += num; // Четные и отрицательные
                } else {
                    oddNegativeSum += num; // Нечетные и отрицательные
                }
            }
        }

        logEventToAllSources(eventSources,"Сумма четных и отрицательных чисел: " + evenNegativeSum);
        logEventToAllSources(eventSources,"Сумма нечетных и отрицательных чисел: " + oddNegativeSum);

    }

}
