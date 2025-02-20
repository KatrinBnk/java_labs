package laba4;

import java.io.*;
import java.net.*;
import java.util.Properties;

/* NOTE: задание на работу (вариант 2)
0 - TCP протокол
3 - указание порта для сервера из файла настроек
2 - "журнал сервера" (путь к нему задается) из командной строки
*/

public class TCPServer {
    private ServerSocket servSocket;
    private String logFilePath;
    private int port;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Ошибка: укажите путь к файлу журнала как аргумент командной строки.");
            System.err.println("Пример: java TCPServer /path/to/server.log");
            System.exit(1);
        }

        TCPServer tcpServer = new TCPServer(args[0]);
        tcpServer.go();
    }

    public TCPServer(String logFilePath) {
        this.logFilePath = logFilePath;

        try {
            Properties props = new Properties();
            props.load(new FileInputStream("server.properties"));
            port = Integer.parseInt(props.getProperty("port"));
        } catch (IOException | NumberFormatException e) {
            System.err.println("Ошибка при чтении файла настроек: " + e.toString());
            System.exit(1);
        }

        try {
            servSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Не удаётся открыть сокет для сервера на порту " + port + ": " + e.toString());
            System.exit(1);
        }
    }

    public void go() {
        System.out.println("Сервер запущен на порту " + port + "...");
        while (true) {
            try {
                Socket socket = servSocket.accept();
                new Thread(new Listener(socket)).start();
            } catch (IOException e) {
                System.err.println("Исключение: " + e.toString());
            }
        }
    }

    class Listener implements Runnable {
        private Socket socket;
        private double result = 0;
        private char lastOperation = '\0';
        private PrintWriter logWriter;

        public Listener(Socket socket) {
            this.socket = socket;
            try {
                logWriter = new PrintWriter(new FileWriter(logFilePath, true), true);
            } catch (IOException e) {
                System.err.println("Ошибка при открытии файла журнала: " + e.toString());
                logWriter = null;
            }
        }

        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                while (true) {
                    String input = reader.readLine();
                    if (input == null) break;

                    if (logWriter != null) {
                        logWriter.println("Получено: " + input);
                    } else {
                        System.err.println("Не удалось записать в журнал: " + input);
                    }

                    // Проверка на команду выхода
                    if ("exit".equalsIgnoreCase(input)) {
                        writer.println("Соединение завершено.");
                        break; // Завершаем цикл для этого клиента
                    }

                    if (input.isEmpty() || "+-=".indexOf(input.charAt(input.length() - 1)) == -1) {
                        writer.println("Ошибка: некорректная операция.");
                        result = 0;
                        lastOperation = '\0';
                        continue;
                    }

                    String numberStr = input.substring(0, input.length() - 1);
                    char operation = input.charAt(input.length() - 1);
                    double number;

                    try {
                        number = Double.parseDouble(numberStr);
                    } catch (NumberFormatException e) {
                        writer.println("Ошибка: введено не число.");
                        result = 0;
                        lastOperation = '\0';
                        continue;
                    }

                    if (lastOperation == '\0') {
                        result = number;
                        if (operation == '=') {
                            writer.println("Результат: " + result);
                            result = 0;
                            lastOperation = '\0';
                            continue;
                        } else {
                            writer.println("Операция принята: " + result);
                        }
                    } else {
                        switch (lastOperation) {
                            case '+': result += number; break;
                            case '-': result -= number; break;
                            default:
                                writer.println("Ошибка: неверная операция.");
                                result = 0;
                                lastOperation = '\0';
                                continue;
                        }

                        if (Double.isInfinite(result) || Double.isNaN(result)) {
                            writer.println("Ошибка: переполнение или недопустимая операция.");
                            result = 0;
                            lastOperation = '\0';
                            continue;
                        }

                        if (operation == '=') {
                            writer.println("Результат: " + result);
                            result = 0;
                            lastOperation = '\0';
                            continue;
                        } else {
                            writer.println("Операция принята: " + result);
                        }
                    }

                    if (operation == '+' || operation == '-') {
                        lastOperation = operation;
                    } else if (operation != '=') {
                        writer.println("Ошибка: недопустимая операция.");
                        result = 0;
                        lastOperation = '\0';
                    }
                }
            } catch (IOException e) {
                System.err.println("Исключение: " + e.toString());
            } finally {
                if (logWriter != null) logWriter.close();
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии сокета: " + e.toString());
                }
            }
        }
    }
}