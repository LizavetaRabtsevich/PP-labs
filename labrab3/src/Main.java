//В каждой строке текста поменять порядок символов на обратный.
//Рабцевич, 6 группа
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите строки текста (пустая строка - конец ввода):");

        while (true) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                break;
            }

            String reversed = new StringBuilder(line).reverse().toString();

            System.out.println("Обратный порядок: " + reversed);
        }
        scanner.close();
    }
}
