package test;

import lab.Main;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    void testSingleLine() {
        List<String> input = Collections.singletonList("Привет");
        List<String> expected = Collections.singletonList("тевирП");
        assertEquals(expected, Main.reverseLines(input));
    }

    @Test
    void testMultipleLines() {
        List<String> input = Arrays.asList("Java", "Лучше", "Всех");
        List<String> expected = Arrays.asList("avaJ", "ешчуЛ", "хесВ");
        assertEquals(expected, Main.reverseLines(input));
    }

    @Test
    void testEmptyInput() {
        List<String> input = Collections.emptyList();
        List<String> expected = Collections.emptyList();
        assertEquals(expected, Main.reverseLines(input));
    }

    @Test
    void testLineWithSpaces() {
        List<String> input = Collections.singletonList("Привет,. мир");
        List<String> expected = Collections.singletonList("римтевирП");
        assertEquals(expected, Main.reverseLines(input));
    }
}
