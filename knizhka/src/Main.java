import java.io.*;
import java.util.*;

public class Main {
    private static List<RecordBook> recordBooks = new ArrayList<>();

    public static void main(String[] args) {
        readFromFile("input.txt");

        writeExcellentStudentsToFile("output.txt");

        System.out.println("Все студенты:");
        for (RecordBook rb : recordBooks) {
            System.out.println(rb);
        }

        System.out.println("\nОтличники:");
        List<RecordBook> excellentStudents = getExcellentStudents();
        for (RecordBook rb : excellentStudents) {
            System.out.println(rb);
        }
    }

    public static void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            RecordBook currentStudent = null;
            RecordBook.Session currentSession = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("Студент:")) {
                    String[] parts = line.substring(9).split(",");
                    String[] nameParts = parts[0].trim().split(" ");
                    String lastName = nameParts[0];
                    String firstName = nameParts[1];
                    String middleName = nameParts[2];
                    int course = Integer.parseInt(parts[1].trim());
                    String group = parts[2].trim();

                    currentStudent = new RecordBook(lastName, firstName, middleName, course, group);
                    recordBooks.add(currentStudent);

                } else if (line.startsWith("Сессия:")) {
                    int sessionNumber = Integer.parseInt(line.substring(8).trim());
                    if (currentStudent != null) {
                        currentSession = currentStudent.createSession(sessionNumber);
                    }

                } else if (line.startsWith("Экзамен:")) {
                    String[] parts = line.substring(9).split(",");
                    String subject = parts[0].trim();
                    int grade = Integer.parseInt(parts[1].trim());

                    if (currentSession != null) {
                        currentSession.addExam(subject, grade);
                    }

                } else if (line.startsWith("Зачет:")) {
                    String[] parts = line.substring(6).split(",");
                    String subject = parts[0].trim();
                    boolean passed = parts[1].trim().equalsIgnoreCase("сдан");

                    if (currentSession != null) {
                        currentSession.addCredit(subject, passed);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public static void writeExcellentStudentsToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            List<RecordBook> excellentStudents = getExcellentStudents();

            for (RecordBook student : excellentStudents) {
                pw.println("=== Отличник ===");
                pw.printf("ФИО: %s %s %s%n", student.getLastName(), student.getFirstName(), student.getMiddleName());
                pw.printf("Курс: %d, Группа: %s%n", student.getCourse(), student.getGroup());

                for (RecordBook.Session session : student.getSessions()) {
                    pw.printf("Сессия %d:%n", session.getSessionNumber());

                    for (RecordBook.Exam exam : session.getExams()) {
                        pw.printf("  Экзамен: %s, Оценка: %d%n", exam.getSubject(), exam.getGrade());
                    }
                }
                pw.println();
            }

            if (excellentStudents.isEmpty()) {
                pw.println("Отличники не найдены.");
            }

        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    public static List<RecordBook> getExcellentStudents() {
        List<RecordBook> excellentStudents = new ArrayList<>();
        for (RecordBook rb : recordBooks) {
            if (rb.isExcellentStudent()) {
                excellentStudents.add(rb);
            }
        }
        return excellentStudents;
    }
}