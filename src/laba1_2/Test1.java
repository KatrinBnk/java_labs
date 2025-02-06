import java.util.ArrayList;

public class Test1 {
    public static void main(String[] args) {
        ArrayList<String> al1 = new ArrayList<>();
        ArrayList<String> al2 = new ArrayList<>();

        for (String x : args) {
            if (Integer.parseInt(x) % 2 == 0) {
                al2.add(x);
            } else {
                al1.add(x);
            }
        }

        System.out.println(al1);
        System.out.println(al2);
    }
}
