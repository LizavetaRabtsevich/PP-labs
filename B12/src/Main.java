import java.io.*;
import java.util.*;

class Line {
    private final double A, B, C;

    public Line(double x1, double y1, double x2, double y2) {
        this.A = y2 - y1;
        this.B = x1 - x2;
        this.C = x2 * y1 - x1 * y2;
        normalize();
    }

    private void normalize() {
        double gcd = gcd(gcd(Math.abs(A), Math.abs(B)), Math.abs(C));

        if (gcd != 0) {
            double a = A / gcd;
            double b = B / gcd;
            double c = C / gcd;

            if (a < 0 || (a == 0 && b < 0) || (a == 0 && b == 0 && c < 0)) {
            }
        }
    }

    private double gcd(double a, double b) {
        if (b < 1e-10) return a;
        return gcd(b, a % b);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Line line = (Line) obj;
        return Math.abs(line.A - A) < 1e-10 &&
                Math.abs(line.B - B) < 1e-10 &&
                Math.abs(line.C - C) < 1e-10;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                Math.round(A * 1000),
                Math.round(B * 1000),
                Math.round(C * 1000)
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (Math.abs(A) > 1e-10) {
            if (Math.abs(A) != 1) sb.append(String.format("%.2f", A));
            sb.append("x");
        }

        if (Math.abs(B) > 1e-10) {
            if (sb.length() > 0) sb.append(B > 0 ? " + " : " - ");
            else if (B < 0) sb.append("-");

            if (Math.abs(Math.abs(B) - 1) > 1e-10)
                sb.append(String.format("%.2f", Math.abs(B)));
            sb.append("y");
        }

        if (Math.abs(C) > 1e-10) {
            if (sb.length() > 0) sb.append(C > 0 ? " + " : " - ");
            else if (C < 0) sb.append("-");
            sb.append(String.format("%.2f", Math.abs(C)));
        }

        if (sb.length() == 0) sb.append("0");
        sb.append(" = 0");

        return sb.toString();
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Введите количество точек N: ");
            int N = scanner.nextInt();

            if (N < 2) {
                System.out.println("Ошибка: должно быть минимум 2 точки");
                return;
            }
            double[][] points = new double[N][2];
            System.out.println("Введите координаты точек (x y):");

            for (int i = 0; i < N; i++) {
                System.out.printf("Точка %d: ", i + 1);
                points[i][0] = scanner.nextDouble();
                points[i][1] = scanner.nextDouble();
            }

            System.out.println("\nВведенные точки:");
            for (int i = 0; i < N; i++) {
                System.out.printf("Точка %d: (%.2f, %.2f)%n", i + 1, points[i][0], points[i][1]);
            }

            String filename = "output.txt";
            findAndSaveLines(points, filename);

        } catch (Exception e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static void findAndSaveLines(double[][] points, String filename) {
        Map<Line, Set<Integer>> linePointsMap = new HashMap<>();

        System.out.println("\nОбработка " + points.length + " точек...");

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double x1 = points[i][0], y1 = points[i][1];
                double x2 = points[j][0], y2 = points[j][1];

                if (Math.abs(x1 - x2) < 1e-10 && Math.abs(y1 - y2) < 1e-10) {
                    continue;
                }

                Line line = new Line(x1, y1, x2, y2);

                Set<Integer> pointIndices = linePointsMap.getOrDefault(line, new HashSet<>());

                pointIndices.add(i);
                pointIndices.add(j);

                linePointsMap.put(line, pointIndices);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("АНАЛИЗ ПРЯМЫХ ДЛЯ " + points.length + " ТОЧЕК");
            writer.println();

            writer.println("Координаты точек:");
            for (int i = 0; i < points.length; i++) {
                writer.printf("Точка %d: (%.2f, %.2f)%n", i + 1, points[i][0], points[i][1]);
            }
            writer.println();

            writer.println("ПРЯМЫЕ, ПРОХОДЯЩИЕ ЧЕРЕЗ 2 И БОЛЕЕ ТОЧЕК:");

            int lineCounter = 0;
            int totalLinesWithMultiplePoints = 0;

            List<Map.Entry<Line, Set<Integer>>> sortedLines = new ArrayList<>(linePointsMap.entrySet());
            sortedLines.sort((e1, e2) -> e2.getValue().size() - e1.getValue().size());

            for (Map.Entry<Line, Set<Integer>> entry : sortedLines) {
                int pointCount = entry.getValue().size();

                if (pointCount >= 2) {
                    lineCounter++;
                    totalLinesWithMultiplePoints++;

                    writer.println("Прямая #" + lineCounter + ": " + entry.getKey());
                    writer.println("Количество точек: " + pointCount);
                    writer.print("Проходит через точки: ");

                    List<Integer> sortedIndices = new ArrayList<>(entry.getValue());
                    Collections.sort(sortedIndices);

                    for (int k = 0; k < sortedIndices.size(); k++) {
                        int idx = sortedIndices.get(k);
                        writer.printf("%d(%.2f,%.2f)",
                                idx + 1, points[idx][0], points[idx][1]);
                        if (k < sortedIndices.size() - 1) writer.print(", ");
                    }
                    writer.println("\n");
                }
            }

            if (totalLinesWithMultiplePoints == 0) {
                writer.println("Не найдено прямых, проходящих через 2 и более точек.");
            } else {
                writer.printf("Всего найдено прямых: %d%n", totalLinesWithMultiplePoints);
            }

            System.out.println("\nРезультат записан в файл: " + filename);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }

        printStatistics(linePointsMap, points);
    }

    private static void printStatistics(Map<Line, Set<Integer>> linePointsMap, double[][] points) {
        System.out.println("\nСТАТИСТИКА:");
        System.out.println("Всего точек: " + points.length);
        System.out.println("Всего уникальных прямых: " + linePointsMap.size());

        Map<Integer, Integer> countStats = new TreeMap<>();
        for (Set<Integer> pointIndices : linePointsMap.values()) {
            int count = pointIndices.size();
            countStats.put(count, countStats.getOrDefault(count, 0) + 1);
        }

        System.out.println("\nРаспределение прямых по количеству точек:");
        for (Map.Entry<Integer, Integer> entry : countStats.entrySet()) {
            System.out.printf("  Через %d точек: %d прямых%n", entry.getKey(), entry.getValue());
        }

        Line maxLine = null;
        int maxPoints = 0;

        for (Map.Entry<Line, Set<Integer>> entry : linePointsMap.entrySet()) {
            if (entry.getValue().size() > maxPoints) {
                maxPoints = entry.getValue().size();
                maxLine = entry.getKey();
            }
        }

        if (maxLine != null && maxPoints > 2) {
            System.out.printf("%nПрямая с наибольшим количеством точек (%d):%n", maxPoints);
            System.out.println("Уравнение: " + maxLine);
        }
    }
}