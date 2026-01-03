import java.io.*;
import java.util.*;

public class StudentFileHandler {
    public static List<Student> readFromFile(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(Student.fromString(line.trim()));
            }
        }
        return students;
    }

    public static void writeToFile(String filename, List<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student s : students) {
                writer.write(s.toString());
                writer.newLine();
            }
        }
    }
}
