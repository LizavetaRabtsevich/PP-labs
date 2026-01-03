//В каждой строке текста поменять порядок символов на обратный.

package lab;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> lines = new ArrayList<>();

        System.out.println("Введите строки текста (пустая строка - конец ввода текста):");

        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                break;
            }
            lines.add(line);
        }

        System.out.println("\nПеревёрнутые строки:");
        List<String> reversed = reverseLines(lines);
        for (String line : reversed) {
            System.out.println(line);
        }

        scanner.close();
    }

    public static List<String> reverseLines(List<String> lines) {
        List<String> result = new ArrayList<>();
        for (String line : lines) {
            String cleaned = line.replaceAll("[\\s,.]", "");
            String reversed = new StringBuilder(cleaned).reverse().toString();
            result.add(reversed);
        }
        return result;
    }
}















//package lab;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        List<String> lines = new ArrayList<>();
//
//        System.out.println("Введите строки текста (пустая строка - конец ввода текста):");
//
//        while (true) {
//            String line = scanner.nextLine();
//            if (line.isEmpty()) {
//                break;
//            }
//            lines.add(line);
//        }
//
//        System.out.println("\nПеревёрнутые строки:");
//        List<String> reversed = reverseLines(lines);
//        for (String line : reversed) {
//            System.out.println(line);
//        }
//
//        scanner.close();
//    }
//
//    public static List<String> reverseLines(List<String> lines) {
//        List<String> result = new ArrayList<>();
//        for (String line : lines) {
//            result.add(new StringBuilder(line).reverse().toString());
//        }
//        return result;
//    }
//}






//package lab;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        List<String> lines = new ArrayList<>();
//
//        System.out.println("Введите строки текста (пустая строка - конец ввода текста):");
//
//        while (true) {
//            String line = scanner.nextLine();
//            if (line.isEmpty()) {
//                break;
//            }
//            lines.add(line);
//        }
//        System.out.println("\nПеревёрнутые строки:");
//        for (String line : lines) {
//            String reversed = new StringBuilder(line).reverse().toString();
//            System.out.println(reversed);
//        }
//        scanner.close();
//    }
//}
