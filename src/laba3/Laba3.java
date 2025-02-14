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

// Интерфейс для обработки события "Обращение к потоку вывода на консоль"
interface IConsoleOutputEvent {
    void handleConsoleOutput();
}

// Интерфейс для обработки события "Обращение к массиву"
interface IArrayAccessEvent {
    void handleArrayAccess();
}

// Интерфейс для обработки события "Обращение к потоку ввода с консоли"
interface IConsoleInputEvent {
    void handleConsoleInput();
}

/**
 class Source {// Класс источника события
 IEv iEv;
 Source (IEv iEv) {
 this.iEv= iEv;
 } // Конструктор
 void genEv() {
 iEv.Handler();
 } // Генерировать событие
 }
 */

// Источиники для каждого события


// Источник события для вывода на консоль
class ConsoleOutputSource {
    IConsoleOutputEvent iEventHandler;

    ConsoleOutputSource(IConsoleOutputEvent iEventHandler) {
        this.iEventHandler = iEventHandler;
    }

    public void generateEvent() {
        iEventHandler.handleConsoleOutput();
    }
}

// Источник события для работы с массивом
class ArrayAccessSource {
    IArrayAccessEvent iEventHandler;

    ArrayAccessSource(IArrayAccessEvent iEventHandler) {
        this.iEventHandler = iEventHandler;
    }

    void generateEvent() {
        iEventHandler.handleArrayAccess();
    }
}

// Источник события для ввода с консоли
class ConsoleInputSource {
    IConsoleInputEvent iEventHandler;

    public ConsoleInputSource(IConsoleInputEvent iEventHandler) {
        this.iEventHandler = iEventHandler;
    }

    public void generateEvent() {
        iEventHandler.handleConsoleInput();
    }
}

/*
class Receiver implements IEv {// Класс приёмника события
    public void Handler() {
        System.out.println ("OK");
    }// Обработчик
}
*/


// Приемник для обработки события вывода на консоль

// Приемник для обработки события вывода на консоль
class ConsoleOutputReceiver implements IConsoleOutputEvent {

    //TODO: заменить на нормальную адаптацию, чтобы не дублировать вывод и в консоль, и в файл по сто миллионов раз
    @Override
    public void handleConsoleOutput() {
        System.out.println("Обращение к потоку вывода на консоль");
    }
}

// Приемник для обработки события работы с массивом
class ArrayAccessReceiver implements IArrayAccessEvent {
    //TODO: заменить на нормальную адаптацию, чтобы не дублировать вывод и в консоль, и в файл по сто миллионов раз
    @Override
    public void handleArrayAccess() {
        System.out.println("Обращение к массиву");
    }
}

// Приемник для обработки события ввода с консоли
class ConsoleInputReceiver implements IConsoleInputEvent {
    public void handleConsoleInput() {
        System.out.println("Обращение к потоку ввода с консоли");
    }
}

public class Laba3 {

    public static void main(String[] args) {

        // Создаем приемники для каждого типа события, передаем комбинированный обработчик
        ConsoleOutputReceiver consoleOutputReceiver = new ConsoleOutputReceiver();
        ArrayAccessReceiver arrayAccessReceiver = new ArrayAccessReceiver();
        ConsoleInputReceiver consoleInputReceiver = new ConsoleInputReceiver();

        // Создаем источники для каждого события
        ConsoleOutputSource consoleOutputSource = new ConsoleOutputSource(consoleOutputReceiver);
        ArrayAccessSource arrayAccessSource = new ArrayAccessSource(arrayAccessReceiver);
        ConsoleInputSource consoleInputSource = new ConsoleInputSource(consoleInputReceiver);

        Scanner scanner = new Scanner(System.in);
        // TODO: логи, которые происходили до создания файла с логами
        consoleOutputSource.generateEvent();
        System.out.print("Введите путь к файлу с данными: ");
        consoleInputSource.generateEvent();
        String filePath = scanner.nextLine();

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


                // Считываем вторую строку, содержащую числа
                line = br.readLine();
                if (line != null) {
                    String[] tokens = line.split("\\s+");
                    for (String token : tokens) {
                        try {
                            numbers.add(Integer.parseInt(token.trim()));
                            arrayAccessSource.generateEvent();
                        } catch (NumberFormatException e) {
                            consoleOutputSource.generateEvent();
                            System.out.println("Некорректное число: " + token);
                        }
                    }
                }
            } finally {
                br.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int evenNegativeSum = 0;
        int oddNegativeSum = 0;

        arrayAccessSource.generateEvent();
        for (int num : numbers) {
            if (num < 0) {
                if (num % 2 == 0) {
                    evenNegativeSum += num; // Четные и отрицательные
                } else {
                    oddNegativeSum += num; // Нечетные и отрицательные
                }
            }
        }

        consoleOutputSource.generateEvent();
        System.out.println("Сумма четных и отрицательных чисел = " + evenNegativeSum);
        consoleOutputSource.generateEvent();
        System.out.println("Сумма нечетных и отрицательных чисел = " + oddNegativeSum);
    }
}


/*
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Интерфейсы для каждого события
interface IConsoleOutputEvent {
    void handleConsoleOutput();
}

interface IArrayAccessEvent {
    void handleArrayAccess();
}

interface IConsoleInputEvent {
    void handleConsoleInput();
}

// Общий интерфейс для обработки событий
interface IEventHandler {
    void handleEvent(String message);
}

// Класс для обработки события вывода в консоль и записи в файл
class ConsoleAndFileEventHandler implements IEventHandler {
    private String fileName;

    public ConsoleAndFileEventHandler(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void handleEvent(String message) {
        // Выводим в консоль
        System.out.println(message);
        // Записываем в файл
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Источник события для вывода на консоль
class ConsoleOutputSource {
    private IConsoleOutputEvent eventHandler;

    public ConsoleOutputSource(IConsoleOutputEvent eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void generateEvent() {
        eventHandler.handleConsoleOutput();
    }
}

// Источник события для работы с массивом
class ArrayAccessSource {
    private IArrayAccessEvent eventHandler;

    public ArrayAccessSource(IArrayAccessEvent eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void generateEvent() {
        eventHandler.handleArrayAccess();
    }
}

// Источник события для ввода с консоли
class ConsoleInputSource {
    private IConsoleInputEvent eventHandler;

    public ConsoleInputSource(IConsoleInputEvent eventHandler) {
        this.eventHandler = eventHandler;
    }

    public void generateEvent() {
        eventHandler.handleConsoleInput();
    }
}

// Приемник для обработки события вывода на консоль
class ConsoleOutputReceiver implements IConsoleOutputEvent {
    private IEventHandler eventHandler;

    public ConsoleOutputReceiver(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void handleConsoleOutput() {
        eventHandler.handleEvent("Обращение к потоку вывода на консоль");
    }
}

// Приемник для обработки события работы с массивом
class ArrayAccessReceiver implements IArrayAccessEvent {
    private IEventHandler eventHandler;

    public ArrayAccessReceiver(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void handleArrayAccess() {
        eventHandler.handleEvent("Обращение к массиву");
    }
}

// Приемник для обработки события ввода с консоли
class ConsoleInputReceiver implements IConsoleInputEvent {
    private IEventHandler eventHandler;

    public ConsoleInputReceiver(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public void handleConsoleInput() {
        eventHandler.handleEvent("Обращение к потоку ввода с консоли");
    }
}

public class TestEvent {
    public static void main(String[] args) {
        // Создаем общий обработчик для вывода в консоль и записи в файл
        ConsoleAndFileEventHandler handler = new ConsoleAndFileEventHandler("output.txt");

        // Создаем приемники для каждого типа события, передаем комбинированный обработчик
        ConsoleOutputReceiver consoleOutputReceiver = new ConsoleOutputReceiver(handler);
        ArrayAccessReceiver arrayAccessReceiver = new ArrayAccessReceiver(handler);
        ConsoleInputReceiver consoleInputReceiver = new ConsoleInputReceiver(handler);

        // Создаем источники для каждого события
        ConsoleOutputSource consoleOutputSource = new ConsoleOutputSource(consoleOutputReceiver);
        ArrayAccessSource arrayAccessSource = new ArrayAccessSource(arrayAccessReceiver);
        ConsoleInputSource consoleInputSource = new ConsoleInputSource(consoleInputReceiver);

        // Генерируем события
        consoleOutputSource.generateEvent();
        arrayAccessSource.generateEvent();
        consoleInputSource.generateEvent();
    }
}

*/