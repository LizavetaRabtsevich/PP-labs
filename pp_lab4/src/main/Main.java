package main;
//Ввести текст и список слов. Для каждого слова из заданного списка
//найти, сколько раз оно встречается в тексте, и рассортировать слова по
//убыванию количества вхождений.

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = "";
        System.out.println("Выберите способ ввода текста:");
        System.out.println("1 - Ввести текст с консоли");
        System.out.println("2 - Считать текст из файла");
        System.out.print("Ваш выбор: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("Введите текст (для завершения введите пустую строку дважды):");
            StringBuilder textBuilder = new StringBuilder();
            String line;
            int emptyLineCount = 0;
            while (emptyLineCount < 2) {
                line = scanner.nextLine();
                if (line.isEmpty()) {
                    emptyLineCount++;
                } else {
                    emptyLineCount = 0;
                    textBuilder.append(line).append(" ");
                }
            }
            text = textBuilder.toString().trim().toLowerCase();
        } else if (choice == 2) {
            System.out.print("Введите путь к файлу: ");
            String filePath = scanner.nextLine();
            try {
                text = readFile(filePath).toLowerCase();
            } catch (IOException e) {
                System.out.println("Ошибка при чтении файла: " + e.getMessage());
                scanner.close();
                return;
            }
        } else {
            System.out.println("Некорректный выбор.");
            scanner.close();
            return;
        }

        System.out.println("Введите слова через пробел:");
        String[] words = scanner.nextLine().toLowerCase().split("\\s+");

        String[] textWords = text.split("[\\s.,!?:;\"()\\[\\]{}]+");

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String word : words) {
            frequencyMap.put(word, 0);
        }

        for (String w : textWords) {
            if (frequencyMap.containsKey(w)) {
                frequencyMap.put(w, frequencyMap.get(w) + 1);
            }
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencyMap.entrySet());
        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        System.out.println("Результат:");
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        scanner.close();
    }

    private static String readFile(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(" ");
            }
        }
        return sb.toString().trim();
    }
}




//import java.util.*;
//import java.io.*;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        String text = "";
//
//        System.out.println("Выберите способ ввода текста:");
//        System.out.println("1 - Ввести текст с консоли");
//        System.out.println("2 - Считать текст из файла");
//        System.out.print("Ваш выбор: ");
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        if (choice == 1) {
//            System.out.println("Введите текст:");
//            text = scanner.nextLine().toLowerCase();
//        } else if (choice == 2) {
//            System.out.print("Введите путь к файлу: ");
//            String filePath = scanner.nextLine();
//            try {
//                text = readFile(filePath).toLowerCase();
//            } catch (IOException e) {
//                System.out.println("Ошибка при чтении файла: " + e.getMessage());
//                scanner.close();
//                return;
//            }
//        } else {
//            System.out.println("Некорректный выбор.");
//            scanner.close();
//            return;
//        }
//
//        System.out.println("Введите слова через пробел:");
//        String[] words = scanner.nextLine().toLowerCase().split("\\s+");
//
//        String[] textWords = text.split("[\\s.,!?:;\"()\\[\\]{}]+");
//
//        Map<String, Integer> frequencyMap = new HashMap<>();
//        for (String word : words) {
//            frequencyMap.put(word, 0);
//        }
//
//        for (String w : textWords) {
//            if (frequencyMap.containsKey(w)) {
//                frequencyMap.put(w, frequencyMap.get(w) + 1);
//            }
//        }
//
//        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencyMap.entrySet());
//
//        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
//
//        System.out.println("Результат:");
//        for (Map.Entry<String, Integer> entry : entries) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }
//
//        scanner.close();
//    }
//
//    private static String readFile(String filePath) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                sb.append(line).append(" ");
//            }
//        }
//        return sb.toString().trim();
//    }
//}
//
