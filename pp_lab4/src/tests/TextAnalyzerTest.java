package tests;

import main.TextAnalyzer;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TextAnalyzerTest {

    @Test
    public void testCountWordFrequencies_basic() {
        String text = "Java is great. Java is fast. I love Java!";
        List<String> words = Arrays.asList("java", "is", "python");

        Map<String, Integer> result = TextAnalyzer.countWordFrequencies(text, words);

        assertEquals(3, result.get("java"));
        assertEquals(2, result.get("is"));
        assertEquals(0, result.get("python"));
    }

    @Test
    public void testCountWordFrequencies_caseInsensitive() {
        String text = "Hello HELLO hello!";
        List<String> words = Arrays.asList("hello");

        Map<String, Integer> result = TextAnalyzer.countWordFrequencies(text, words);

        assertEquals(3, result.get("hello"));
    }

    @Test
    public void testSortByFrequencyDesc() {
        Map<String, Integer> freqMap = new HashMap<>();
        freqMap.put("a", 2);
        freqMap.put("b", 5);
        freqMap.put("c", 1);

        List<Map.Entry<String, Integer>> sorted = TextAnalyzer.sortByFrequencyDesc(freqMap);

        assertEquals("b", sorted.get(0).getKey());
        assertEquals("a", sorted.get(1).getKey());
        assertEquals("c", sorted.get(2).getKey());
    }

    @Test
    public void testEmptyText() {
        String text = "";
        List<String> words = Arrays.asList("hello", "world");

        Map<String, Integer> result = TextAnalyzer.countWordFrequencies(text, words);

        assertEquals(0, result.get("hello"));
        assertEquals(0, result.get("world"));
    }

    @Test
    public void testEmptyWordList() {
        String text = "Some text with words";
        List<String> words = new ArrayList<>();

        Map<String, Integer> result = TextAnalyzer.countWordFrequencies(text, words);

        assertTrue(result.isEmpty());
    }
}
