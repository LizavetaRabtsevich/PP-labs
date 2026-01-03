//#12 task labrab 1
//Рабцевич Елизавета, 6 группа, 2 курс

import java.util.Scanner;

class SeriesCalculator {
    private double x;
    private double e;

    public SeriesCalculator(double x, int k) {
        this.x = x;
        this.e = Math.pow(10, -k);
    }

    public double calculateSeries() {
        double term = x;
        double sum = 0;
        int n = 1;

        while (Math.abs(term) >= e) {
            sum += term;
            n += 2;
            term = Math.pow(x, n) / n;
        }

        return 2 * sum;
    }

    public double calculateExact() {
        return Math.log((1 + x) / (1 - x));
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введите x (-1 < x < 1): ");
            double x = scanner.nextDouble();
            if (x <= -1 || x >= 1) {
                throw new IllegalArgumentException("Значение x должно быть в интервале (-1, 1).");
            }

            System.out.print("Введите k (натуральное число): ");
            int k = scanner.nextInt();
            if (k <= 0) {
                throw new IllegalArgumentException("k должно быть натуральным числом.");
            }

            SeriesCalculator calc = new SeriesCalculator(x, k);

            double approx = calc.calculateSeries();
            double exact = calc.calculateExact();

            System.out.printf("Приближённое значение: %.3f%n", approx);
            System.out.printf("Точное значение:       %.3f%n", exact);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
