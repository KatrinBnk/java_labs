/*
В последовательности чисел найти сумму «чётных и отрицательных» и «нечётных и отрицательных» чисел.
*/


public class Laba1 {
    public static void main(String[] args) {
        int summ1 = 0;
        int summ2 = 0;
        int curr = 0;

        for (String x : args) {
            curr = Integer.parseInt(x);
            if (curr < 0) {
                if (curr % 2 == 0) {
                    summ1 += curr;
                } else {
                    summ2 += curr;
                }
            }

        }

        System.out.println("сумма четных и отрицательных = "+ summ1 + ", сумма нечетных и отрицательных = " + summ2);
    }
}

