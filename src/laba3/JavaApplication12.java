package laba3;
/* Пример работы с текстовым файлом */
import java.io.*;

public class JavaApplication12 {
    public static void main(String[] args) {
        // Создание файла и запись в него -------------------------------
        String filename = "log.txt";
        File f = new File(filename);
        try {
            if(!f.exists()) f.createNewFile();
            PrintWriter pw = new PrintWriter(f.getAbsoluteFile());
            try {
                pw.println("2 ~!");
                pw.println("4 ^!");
                pw.println("6 #!");
                pw.println("8 @!");
            } finally {
                pw.close();
            }
        } catch(IOException e){throw new RuntimeException();}

        // Чтение из файла -------------------------------------------
        StringBuilder sb = new StringBuilder();
        if(f.exists()){
            try {
                BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
                try {
                    String s;
                    while((s = br.readLine()) != null){// построчное чтение
                        sb.append(s);
                        sb.append("\n");
                    }
                } finally {
                    br.close();
                }
            } catch(IOException e){throw new RuntimeException();}
        }

        System.out.print(sb.toString());// sb.toString() хранится текст файла
        /* Для обновления файла можно сперва прочитать файл в переменную типа String,
           как это сделано в секции чтение из файла,
           а затем, сложив его с новым содержимым, записать в файл*/

        // Удаление файла -------------------------------------------
        f.delete();
    }
}
