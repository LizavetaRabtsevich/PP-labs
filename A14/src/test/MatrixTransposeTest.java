package test;

import main.Main;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixTransposeTest {

    @Test
    public void testTranspose3x3() {
        int[][] input = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] expected = {
                {1, 4, 7},
                {2, 5, 8},
                {3, 6, 9}
        };

        int[][] actual = Main.transposeMatrix(input);
        assertArrayEquals(expected, actual, "3x3 матрица должна быть правильно транспонирована");
    }

    @Test
    public void testTranspose1x1() {
        int[][] input = {
                {42}
        };

        int[][] expected = {
                {42}
        };

        int[][] actual = Main.transposeMatrix(input);
        assertArrayEquals(expected, actual, "1x1 матрица должна оставаться неизменной");
    }

    @Test
    public void testTranspose2x2() {
        int[][] input = {
                {1, 2},
                {3, 4}
        };

        int[][] expected = {
                {1, 3},
                {2, 4}
        };

        int[][] actual = Main.transposeMatrix(input);
        assertArrayEquals(expected, actual, "2x2 матрица должна быть правильно транспонирована");
    }

    @Test
    public void testTransposeEmptyMatrix() {
        int[][] input = new int[0][0];
        int[][] expected = new int[0][0];

        int[][] actual = Main.transposeMatrix(input);
        assertArrayEquals(expected, actual, "Пустая матрица должна возвращать пустую матрицу");
    }

    private void assertMatrixEquals(int[][] expected, int[][] actual, String message) {
        assertEquals(expected.length, actual.length, message + ": количество строк не совпадает");
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i], message + ": несовпадение в строке " + i);
        }
    }
}
