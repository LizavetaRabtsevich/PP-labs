package test;

import lab.MatrixSolver;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixSolverTest {

    @Test
    void testMultipleTwiceNumbers() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 2, 1}
        };
        MatrixSolver solver = new MatrixSolver(matrix);
        assertEquals(2, solver.findSolution());
    }

    @Test
    void testNoTwiceNumbers() {
        int[][] matrix = {
                {5, 5, 5},
                {7, 8, 9}
        };
        MatrixSolver solver = new MatrixSolver(matrix);
        assertEquals(Integer.MIN_VALUE, solver.findSolution());
    }

    @Test
    void testSingleTwiceNumber() {
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 1}
        };
        MatrixSolver solver = new MatrixSolver(matrix);
        assertEquals(1, solver.findSolution());
    }

    @Test
    void testMultipleCandidates() {
        int[][] matrix = {
                {10, 20, 10},
                {30, 20, 40}
        };
        MatrixSolver solver = new MatrixSolver(matrix);
        assertEquals(20, solver.findSolution());
    }
}
