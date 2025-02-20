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

// Интерфейс для обработки события "Обращение к потоку вывода на консоль"
interface IConsoleOutputEvent {
    void handleConsoleOutput(String message, String filePath, Boolean... isDeferredMessage);
}

// Интерфейс для обработки события "Обращение к массиву"
interface IArrayAccessEvent {
    void handleArrayAccess(String message, String filePath, Boolean... isDeferredMessage);
}

// Интерфейс для обработки события "Обращение к потоку ввода с консоли"
interface IConsoleInputEvent {
    void handleConsoleInput(String message, String filePath, Boolean... isDeferredMessage);
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
    List<String> deferredMessages = new ArrayList<>();

    ConsoleOutputSource(IConsoleOutputEvent iEventHandler) {
        this.iEventHandler = iEventHandler;
    }

    public void generateEvent(String message, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            deferredMessages.add(message);
            iEventHandler.handleConsoleOutput(message, null);
        } else {
            iEventHandler.handleConsoleOutput(message, filePath);
        }
    }

    public void processDeferredMessages(String filePath) {
        for (String message : deferredMessages) {
            iEventHandler.handleConsoleOutput(message, filePath, Boolean.TRUE);
        }
        deferredMessages.clear();
    }
}

// Источник события для работы с массивом
class ArrayAccessSource {
    IArrayAccessEvent iEventHandler;
    List<String> deferredMessages = new ArrayList<>();

    ArrayAccessSource(IArrayAccessEvent iEventHandler) {
        this.iEventHandler = iEventHandler;
    }

    public void generateEvent(String message, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            deferredMessages.add(message);
            iEventHandler.handleArrayAccess(message, null);
        } else {
            iEventHandler.handleArrayAccess(message, filePath);
        }
    }
}

// Источник события для ввода с консоли
class ConsoleInputSource {
    IConsoleInputEvent iEventHandler;
    List<String> deferredMessages = new ArrayList<>();

    public ConsoleInputSource(IConsoleInputEvent iEventHandler) {
        this.iEventHandler = iEventHandler;
    }

    public void generateEvent(String message, String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            deferredMessages.add(message);
            iEventHandler.handleConsoleInput(message, null);
        } else {
            iEventHandler.handleConsoleInput(message, filePath);
        }
    }

    public void processDeferredMessages(String filePath) {
        for (String message : deferredMessages) {
            iEventHandler.handleConsoleInput(message, filePath, Boolean.TRUE);
        }
        deferredMessages.clear();
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
class ConsoleOutputReceiver implements IConsoleOutputEvent {

    public void handleConsoleOutput(String message, String filePath, Boolean... isDeferredMessage) {

        if (isDeferredMessage.length == 0 || !isDeferredMessage[0] || filePath == null) {
            System.out.println("\u001B[33m" + "Обращение к потоку вывода на консоль: " + message + "\u001B[0m");
        }

        if (filePath != null) {
            // Записываем в файл
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.write("Обращение к потоку вывода на консоль: " + message );
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }
    }
}

// Приемник для обработки события работы с массивом
class ArrayAccessReceiver implements IArrayAccessEvent {

    public void handleArrayAccess(String message, String filePath, Boolean... isDeferredMessage) {

        if (isDeferredMessage.length == 0 || !isDeferredMessage[0]  || filePath == null) {
            System.out.println("\u001B[33m" + "Обращение к массиву: " + message + "\u001B[0m");
        }

        if (filePath != null) {
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.write("Обращение к массиву: " + message );
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }
    }
}

// Приемник для обработки события ввода с консоли
class ConsoleInputReceiver implements IConsoleInputEvent {
    public void handleConsoleInput(String message, String filePath, Boolean... isDeferredMessage) {

        if (isDeferredMessage.length == 0 || !isDeferredMessage[0]  || filePath == null) {
            System.out.println("\u001B[33m" + "Обращение к потоку ввода с консоли: " + message + "\u001B[0m");
        }

        if (filePath != null) {
            try {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.write("Обращение к потоку ввода с консоли: " + message);
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл: " + e.getMessage());
            }
        }
    }
}

// c:\java_labs\java_labs\src\laba3\input.txt

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
        consoleOutputSource.generateEvent(" вывод сообщения 'Введите путь к файлу с данными:'", null);
        System.out.print("Введите путь к файлу с данными: ");
        consoleInputSource.generateEvent(" запрос пути к файлу с данными ", null);
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

                consoleOutputSource.processDeferredMessages(logFilePath);
                consoleInputSource.processDeferredMessages(logFilePath);


                // Считываем вторую строку, содержащую числа
                line = br.readLine();
                if (line != null) {
                    String[] tokens = line.split("\\s+");
                    for (String token : tokens) {
                        try {
                            numbers.add(Integer.parseInt(token.trim()));
                            arrayAccessSource.generateEvent("добавление элемента " + token, logFilePath);
                        } catch (NumberFormatException e) {
                            consoleOutputSource.generateEvent("встречен некорректный элемент '" + token + "'", logFilePath);
                            System.out.println("Некорректный элемент: " + token);
                        }
                    }
                }
            } finally {
                br.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        int evenNegativeSum = 0;
        int oddNegativeSum = 0;

        arrayAccessSource.generateEvent("обращение к массиву", logFilePath);
        for (int num : numbers) {
            if (num < 0) {
                if (num % 2 == 0) {
                    evenNegativeSum += num; // Четные и отрицательные
                } else {
                    oddNegativeSum += num; // Нечетные и отрицательные
                }
            }
        }

        consoleOutputSource.generateEvent("вывод суммы четных и отрицательных чисел = " + evenNegativeSum, logFilePath);
        System.out.println("Сумма четных и отрицательных чисел = " + evenNegativeSum);
        consoleOutputSource.generateEvent("вывод суммы нечетных и отрицательных чисел = " + oddNegativeSum, logFilePath);
        System.out.println("Сумма нечетных и отрицательных чисел = " + oddNegativeSum);
    }
}