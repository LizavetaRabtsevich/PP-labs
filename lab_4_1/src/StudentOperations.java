import java.util.*;
import java.util.stream.Collectors;

public class StudentOperations {
    public static List<Student> union(List<Student> a, List<Student> b) {
        Set<Student> result = new HashSet<>(a);
        result.addAll(b);
        return new ArrayList<>(result);
    }

    public static List<Student> intersection(List<Student> a, List<Student> b) {
        Set<Student> setB = new HashSet<>(b);
        return a.stream().filter(setB::contains).collect(Collectors.toList());
    }

    public static List<Student> difference(List<Student> a, List<Student> b) {
        Set<Student> setB = new HashSet<>(b);
        return a.stream().filter(s -> !setB.contains(s)).collect(Collectors.toList());
    }
}
