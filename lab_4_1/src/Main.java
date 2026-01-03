import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя первого файла: ");
        String file1 = scanner.nextLine();

        System.out.print("Введите имя второго файла: ");
        String file2 = scanner.nextLine();

        System.out.print("Введите имя выходного файла: ");
        String outputFile = scanner.nextLine();

        System.out.println("Выберите операцию:");
        System.out.println("1 - Объединение");
        System.out.println("2 - Пересечение");
        System.out.println("3 - Разность");

        int choice = scanner.nextInt();

        try {
            List<Student> list1 = StudentFileHandler.readFromFile(file1);
            List<Student> list2 = StudentFileHandler.readFromFile(file2);
            List<Student> result = new ArrayList<>();

            switch (choice) {
                case 1 -> result = StudentOperations.union(list1, list2);
                case 2 -> result = StudentOperations.intersection(list1, list2);
                case 3 -> result = StudentOperations.difference(list1, list2);
                default -> System.out.println("Неверный выбор!");
            }

            StudentFileHandler.writeToFile(outputFile, result);
            System.out.println("Операция завершена. Результат записан в " + outputFile);

        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлами: " + e.getMessage());
        }
    }
}
