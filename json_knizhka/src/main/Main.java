package com.university;

import java.io.*;
import java.util.*;

public class Main {
    private static List<RecordBook> recordBooks = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("=== Система зачетных книжек ===");

            // Чтение из JSON
            System.out.println("Чтение данных из JSON файла...");
            recordBooks = JSONService.readFromJSON("students.json");

            // Получение отличников
            List<RecordBook> excellentStudents = getExcellentStudents();

            // Запись отличников в JSON
            System.out.println("Запись отличников в JSON...");
            JSONService.writeExcellentStudentsToJSON(excellentStudents, "excellent_students.json");

            // Запись в текстовый файл
            writeExcellentStudentsToTextFile(excellentStudents, "excellent_students.txt");

            // Дополнительно: запись в XML
            JSONService.writeToXML(excellentStudents, "excellent_students.xml");

            // Вывод результатов
            printStatistics(excellentStudents);

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            e.printStackTrace();
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

    private static void writeExcellentStudentsToTextFile(List<RecordBook> excellentStudents, String filename) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.println("ОТЧЕТ ПО ОТЛИЧНИКАМ");
            pw.println("===================");

            if (excellentStudents.isEmpty()) {
                pw.println("Отличники не найдены.");
            } else {
                for (RecordBook student : excellentStudents) {
                    pw.println("\n=== Отличник ===");
                    pw.printf("ФИО: %s %s %s%n", student.getLastName(), student.getFirstName(), student.getMiddleName());
                    pw.printf("Курс: %d, Группа: %s%n", student.getCourse(), student.getGroup());

                    for (RecordBook.Session session : student.getSessions()) {
                        pw.printf("Сессия %d:%n", session.getSessionNumber());

                        pw.println("  Экзамены:");
                        for (RecordBook.Exam exam : session.getExams()) {
                            pw.printf("    %s: %d%n", exam.getSubject(), exam.getGrade());
                        }

                        pw.println("  Зачеты:");
                        for (RecordBook.Credit credit : session.getCredits()) {
                            pw.printf("    %s: %s%n", credit.getSubject(), credit.isPassed() ? "сдан" : "не сдан");
                        }
                    }
                }
            }
        }
    }

    private static void printStatistics(List<RecordBook> excellentStudents) {
        System.out.println("\n=== СТАТИСТИКА ===");
        System.out.printf("Всего студентов: %d%n", recordBooks.size());
        System.out.printf("Отличников: %d%n", excellentStudents.size());

        System.out.println("\n=== ОТЛИЧНИКИ ===");
        if (excellentStudents.isEmpty()) {
            System.out.println("Отличники не найдены.");
        } else {
            excellentStudents.forEach(System.out::println);
        }

        System.out.println("\nФайлы успешно созданы:");
        System.out.println("- excellent_students.json (JSON формат)");
        System.out.println("- excellent_students.txt (текстовый формат)");
        System.out.println("- excellent_students.xml (XML формат)");
    }
}