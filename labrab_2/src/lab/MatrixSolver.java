package lab;

import java.util.*;

public class MatrixSolver {

    private int[][] matrix;

    public MatrixSolver(int rows, int cols) {
        matrix = new int[rows][cols];
        fillMatrix();
    }
    public MatrixSolver(int[][] customMatrix) {
        this.matrix = customMatrix;
    }

    private void fillMatrix() {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = random.nextInt(101);
            }
        }
    }

    public void printMatrix() {
        System.out.println("\nМатрица:");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%3d ", val);
            }
            System.out.println();
        }
    }

    public int findSolution() {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int current = matrix[i][j];
                int count = 0;

                for (int k = 0; k < matrix.length; k++) {
                    for (int l = 0; l < matrix[k].length; l++) {
                        if (matrix[k][l] == current) {
                            count++;
                        }
                    }
                }

                if (count == 2 && current > max) {
                    max = current;
                }
            }
        }
        return max;
    }
}


//    public int findSolution() {
//        Map<Integer, Integer> frequencyMap = new HashMap<>();
//
//        for (int[] row : matrix) {
//            for (int val : row) {
//                frequencyMap.put(val, frequencyMap.getOrDefault(val, 0) + 1);
//            }
//        }
//
//        int max = Integer.MIN_VALUE;
//        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
//            if (entry.getValue() == 2 && entry.getKey() > max) {
//                max = entry.getKey();
//            }
//        }
//
//        return max;
//    }
//}
