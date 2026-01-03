package main;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        String inputFileName = "input.txt";
        String outputFileName = "output.txt";
        List<int[]> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                int[] row = new int[tokens.length];
                for (int i = 0; i < tokens.length; i++) {
                    row[i] = Integer.parseInt(tokens[i]);
                }
                rows.add(row);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        int size = rows.size();
        for (int[] row : rows) {
            if (row.length != size) {
                System.err.println("Матрица не квадратная.");
                return;
            }
        }

        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i] = rows.get(i);
        }

        int[][] transposed = transposeMatrix(matrix);

        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFileName))) {
            printMatrix("Исходная матрица:", matrix, writer);
            printMatrix("\nТранспонированная матрица:", transposed, writer);
            System.out.println("Результат записан в файл: " + outputFileName);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    public static int[][] transposeMatrix(int[][] matrix) {
        int size = matrix.length;
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    public static void printMatrix(String title, int[][] matrix, PrintWriter writer) {
        System.out.println(title);
        writer.println(title);

        for (int[] row : matrix) {
            StringBuilder sb = new StringBuilder();
            for (int val : row) {
                sb.append(val).append("\t");
            }
            String line = sb.toString().trim();
            System.out.println(line);
            writer.println(line);
        }
    }
}
