package laba4;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/* NOTE: задание на работу (вариант 2)
0 - TCP протокол
2 - адрес и порт сервера в клиенте из командной строки
2 - "журнал клиента" (путь к нему задается) из командной строки
*/

public class TCPClient {
    private static String host;
    private static int port;
    private static String logFilePath;

    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Ошибка: укажите хост, порт и путь к файлу журнала как аргументы командной строки.");
            System.exit(1);
        }

        host = args[0];
        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: порт должен быть числом.");
            System.exit(1);
        }
        logFilePath = args[2];

        Scanner scanner = new Scanner(System.in);
        PrintWriter logWriter = null;
        Socket socket = null;

        try {
            socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            try {
                logWriter = new PrintWriter(new FileWriter(logFilePath, true), true);
            } catch (IOException e) {
                System.err.println("Ошибка при открытии файла журнала: " + e.toString());
            }

            System.out.println("\u001B[33m" + "Введите число и операцию (+, -, =) или 'exit' для завершения:" + "\u001B[0m");

            while (true) {
                String input = scanner.nextLine();

                // Проверка на команду выхода
                if ("exit".equalsIgnoreCase(input)) {
                    writer.println(input);
                    break;
                }

                writer.println(input);
                String response = reader.readLine();
                if (response != null) {
                    System.out.println("\u001B[36m" + "Ответ от сервера: " + response+ "\u001B[0m");
                    if (logWriter != null) {
                        logWriter.println("Получено от сервера: " + response);
                    } else {
                        System.err.println("Не удалось записать в журнал: " + response);
                    }
                } else {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Исключение: " + e.toString());
        } finally {
            if (logWriter != null) {
                logWriter.close();
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Ошибка при закрытии сокета: " + e.toString());
                }
            }
            scanner.close();
        }
    }
}