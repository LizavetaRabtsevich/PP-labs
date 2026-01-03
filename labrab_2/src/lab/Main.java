package lab;//Рабцевич, 6 группа
//Найти максимальное из чисел, встречающихся в заданной матрице ровно два раза.

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();

        MatrixSolver solver = new MatrixSolver(rows, cols);

        solver.printMatrix();

        int result = solver.findSolution();
        if (result == Integer.MIN_VALUE) {
            System.out.println("\nТаких чисел нет.");
        } else {
            System.out.println("\nМаксимальное число, встречающееся ровно два раза: " + result);
        }
    }
}
