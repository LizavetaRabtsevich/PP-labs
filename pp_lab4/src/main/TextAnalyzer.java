package main;

import java.util.*;

public class TextAnalyzer {

    public static Map<String, Integer> countWordFrequencies(String text, List<String> words) {
        String[] textWords = text.toLowerCase().split("[\\s.,!?:;\"()\\[\\]{}]+");
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String word : words) {
            frequencyMap.put(word.toLowerCase(), 0);
        }

        for (String w : textWords) {
            if (frequencyMap.containsKey(w)) {
                frequencyMap.put(w, frequencyMap.get(w) + 1);
            }
        }

        return frequencyMap;
    }

    public static List<Map.Entry<String, Integer>> sortByFrequencyDesc(Map<String, Integer> frequencyMap) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequencyMap.entrySet());
        entries.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return entries;
    }
}
