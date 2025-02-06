package laba3;

/* Пример ввод с консоли */
import java.util.*;

public class JavaApplication13 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = 0;
        System.out.print("Введите целое число: ");
        // возвращает истину если с потока ввода можно считать целое число
        if(sc.hasNextInt()) {
            /* Аналогичный метод hasNextDouble(), применённый объекту класса Scanner, проверяет,
               можно ли считать с потока ввода вещественное число типа double, а метод nextDouble() —
               считывает его. */
            // считывает целое число с потока ввода и сохраняем в переменную
            i = sc.nextInt();
            System.out.println(i*2);
        } else {
            System.out.println("См. выше ^^^");
        }

        /* Метод nextLine(), позволяющий считывать целую последовательность символов, т.е. строку.
           Далее создаётся два таких объекта, потом в них поочередно записывается ввод пользователя,
           на экран выводится одна строка, полученная объединением введённых последовательностей символов. */
        Scanner sc2 = new Scanner(System.in);
        String s1, s2;
        s1 = sc2.nextLine();
        s2 = sc2.nextLine();
        System.out.println(s1 + s2);
    }
}
