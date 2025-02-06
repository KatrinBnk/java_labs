/*
Задание: В последовательности чисел найти сумму «чётных и отрицательных» и «нечётных и отрицательных» чисел.
000001011 - код
исключительные ситуации:
1. В массиве число элементов меньше указанного
2. В строке отсутствует какой-то символ
4. В массиве число элементов больше указанного
*/

package laba1_2;
import java.util.ArrayList;

interface IOperations {
    int getSumOfEvenNegative(ArrayList<Integer> numbers);
    int getSumOfOddNegative(ArrayList<Integer> numbers);
}

interface IConsts{
    int MAX_ARRAY_SIZE = 5;
    char MISS_CHARACTER = 'z';
}

interface IExceptions {
    String EXCEPT_ARRAY_SIZE_TOO_SMALL = "EXCEPTION: В массиве число элементов меньше указанного ";
    String EXCEPT_ARRAY_SIZE_TOO_LARGE = "EXCEPTION: В массиве число элементов больше указанного ";
    String EXCEPT_MISSING_SYMBOL = "EXCEPTION: В строке отсутствует символ ";
}


class Laba2_Calc implements IOperations, IExceptions, IConsts {

    public int getSumOfEvenNegative(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int num : numbers) {
            if (num < 0 && num % 2 == 0) {
                sum += num;
            }
        }
        return sum;
    }

    public int getSumOfOddNegative(ArrayList<Integer> numbers) {
        int sum = 0;
        for (int num : numbers) {
            if (num < 0 && num % 2 != 0) {
                sum += num;
            }
        }
        return sum;
    }

    // Проверка на количество элементов в массиве
    public void checkArraySize( ArrayList<Integer> numbers) throws ArraySizeException {
        if (numbers.size() < MAX_ARRAY_SIZE) {
            throw new ArraySizeException(EXCEPT_ARRAY_SIZE_TOO_SMALL);
        } else if (numbers.size() > MAX_ARRAY_SIZE) {
            throw new ArraySizeException(EXCEPT_ARRAY_SIZE_TOO_LARGE);
        }
    }

    // Проверка на отсутствие символа
    public void checkMissingSymbol(String[] args) throws MissingSymbolException {
        boolean symbolFound = false;
        for (String arg : args) {
            if (arg.indexOf(MISS_CHARACTER) == -1) {
                symbolFound = true;
                break;
            }
        }
        if (symbolFound) {
            throw new MissingSymbolException(EXCEPT_MISSING_SYMBOL);
        }
    }

}

class ArraySizeException extends Exception {
    public ArraySizeException(String message) {
        super(message + IConsts.MAX_ARRAY_SIZE);
    }
}

class MissingSymbolException extends Exception {
    public MissingSymbolException(String message) {
        super(message + IConsts.MISS_CHARACTER);
    }
}

public class Laba2 {
    public static void main(String[] args) {
        Laba2_Calc laba2 = new Laba2_Calc();

        //Проверка на отстутствие символа
        try{
            laba2.checkMissingSymbol(args);
        } catch (MissingSymbolException e){
            System.out.println(e);
        }

        // Преобразуем строки в массив целых чисел
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            try {
                numbers.add(Integer.parseInt(args[i]));
            } catch (Exception e){
                System.out.println("Аругемент '" + args[i] + "' не был обработан - не число");
            }
        }

        //Проверяем размер больше, меньше
        try {
            laba2.checkArraySize(numbers);
        } catch (ArraySizeException e) {
            System.out.println(e);
        }

        int summ1 = laba2.getSumOfEvenNegative(numbers);
        System.out.println("Сумма чётных отрицательных чисел: " + summ1);

        int summ2 = laba2.getSumOfOddNegative(numbers);
        System.out.println("Сумма нечётных отрицательных чисел: " + summ2);
    }
}

