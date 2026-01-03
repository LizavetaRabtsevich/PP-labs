package org.example;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class Main {

    public static void main(String[] args) {
        List<StudentExamInfo> allStudentsData = readFromJsonFile("input.json");

        if (allStudentsData == null || allStudentsData.isEmpty()) {
            System.out.println("Ошибка: Не удалось прочитать данные из input.json");
            System.out.println("Убедитесь, что файл input.json существует и содержит данные");
            return;
        }

        System.out.println("Данные успешно прочитаны из input.json");

        List<GradeBook> gradeBooks = convertToGradeBooks(allStudentsData);

        List<StudentExamInfo> excellentStudents = findExcellentStudents(gradeBooks);

        writeToTxtFile("output.txt", excellentStudents);
        System.out.println("Данные об отличниках записаны в output.txt");

        printResults(gradeBooks, excellentStudents);
    }

    private static List<StudentExamInfo> readFromJsonFile(String filename) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filename)) {
            Type listType = new TypeToken<List<StudentExamInfo>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + filename + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static List<GradeBook> convertToGradeBooks(List<StudentExamInfo> allData) {
        Map<String, GradeBook> studentsMap = new HashMap<>();
        Map<String, Map<Integer, GradeBook.Session>> sessionsMap = new HashMap<>();

        for (StudentExamInfo data : allData) {
            String studentKey = data.lastName + "|" + data.firstName + "|" + data.middleName + "|" + data.course + "|" + data.group;

            if (!studentsMap.containsKey(studentKey)) {
                GradeBook student = new GradeBook(
                        data.lastName,
                        data.firstName,
                        data.middleName,
                        data.course,
                        data.group
                );
                studentsMap.put(studentKey, student);
                sessionsMap.put(studentKey, new HashMap<>());
            }

            GradeBook student = studentsMap.get(studentKey);
            Map<Integer, GradeBook.Session> studentSessions = sessionsMap.get(studentKey);

            if (!studentSessions.containsKey(data.sessionNumber)) {
                GradeBook.Session session = student.new Session(data.sessionNumber);
                studentSessions.put(data.sessionNumber, session);
                student.addSession(session);

                session.addCredit(student.new Credit(data.subjectName + " (зачет)", true));
            }

            GradeBook.Session session = studentSessions.get(data.sessionNumber);
            session.addExam(student.new Exam(data.subjectName, data.grade));
        }

        return new ArrayList<>(studentsMap.values());
    }

    private static List<StudentExamInfo> findExcellentStudents(List<GradeBook> gradeBooks) {
        List<StudentExamInfo> excellentStudents = new ArrayList<>();

        for (GradeBook student : gradeBooks) {
            if (student.isExcellentStudent()) {
                for (GradeBook.Session session : student.getSessions()) {
                    for (GradeBook.Exam exam : session.getExams()) {
                        excellentStudents.add(new StudentExamInfo(
                                student.getLastName(),
                                student.getFirstName(),
                                student.getMiddleName(),
                                student.getCourse(),
                                student.getGroup(),
                                session.getSessionNumber(),
                                exam.getSubjectName(),
                                exam.getGrade()
                        ));
                    }
                }
            }
        }

        return excellentStudents;
    }

    private static void writeToTxtFile(String filename, List<StudentExamInfo> excellentStudents) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ОТЧЕТ ОБ ОТЛИЧНИКАХ");
            writer.println();

            Map<String, List<StudentExamInfo>> studentsMap = new LinkedHashMap<>();
            for (StudentExamInfo exam : excellentStudents) {
                String studentKey = exam.lastName + " " + exam.firstName + " " + exam.middleName;
                studentsMap.computeIfAbsent(studentKey, k -> new ArrayList<>()).add(exam);
            }

            int studentCount = 1;
            for (Map.Entry<String, List<StudentExamInfo>> entry : studentsMap.entrySet()) {
                writer.printf("СТУДЕНТ %d:%n", studentCount++);
                writer.printf("ФИО: %s%n", entry.getKey());

                StudentExamInfo firstExam = entry.getValue().get(0);
                writer.printf("Курс: %d%n", firstExam.course);
                writer.printf("Группа: %s%n", firstExam.group);
                writer.println();

                writer.println("ЭКЗАМЕНЫ:");
                writer.printf("%-3s %-8s %-25s %-10s%n", "№", "Сессия", "Предмет", "Оценка");

                int examCount = 1;
                for (StudentExamInfo exam : entry.getValue()) {
                    writer.printf("%-3d %-8d %-25s %-10d%n",
                            examCount++, exam.sessionNumber, exam.subjectName, exam.grade);
                }

                writer.printf("Количество экзаменов: %d%n", entry.getValue().size());
                writer.printf("Средний балл: %.2f%n", calculateAverageGrade(entry.getValue()));
                writer.println();
                writer.println("ЗАЧЕТЫ: Все сданы успешно");
                writer.println();
            }

            writer.println("ОБЩАЯ СТАТИСТИКА:");
            writer.printf("Всего отличников: %d%n", studentsMap.size());
            writer.printf("Всего экзаменов: %d%n", excellentStudents.size());
            writer.printf("Общий средний балл: %.2f%n", calculateAverageGrade(excellentStudents));

        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private static double calculateAverageGrade(List<StudentExamInfo> exams) {
        if (exams.isEmpty()) return 0.0;
        double sum = 0;
        for (StudentExamInfo exam : exams) {
            sum += exam.grade;
        }
        return sum / exams.size();
    }

    private static void printResults(List<GradeBook> allStudents, List<StudentExamInfo> excellentStudents) {
        System.out.println("\n=== ВСЕ СТУДЕНТЫ ===");
        for (GradeBook student : allStudents) {
            String status = student.isExcellentStudent() ? " - ОТЛИЧНИК" : "";
            System.out.printf("%s %s %s - %s курс, группа %s%s%n",
                    student.getLastName(), student.getFirstName(), student.getMiddleName(),
                    student.getCourse(), student.getGroup(), status);
        }

        System.out.println("\n=== ОТЛИЧНИКИ ===");
        Map<String, List<StudentExamInfo>> studentsMap = new LinkedHashMap<>();
        for (StudentExamInfo exam : excellentStudents) {
            String studentKey = exam.lastName + " " + exam.firstName + " " + exam.middleName;
            studentsMap.computeIfAbsent(studentKey, k -> new ArrayList<>()).add(exam);
        }

        for (Map.Entry<String, List<StudentExamInfo>> entry : studentsMap.entrySet()) {
            System.out.printf("%s - %d экзаменов, средний балл: %.2f%n",
                    entry.getKey(), entry.getValue().size(), calculateAverageGrade(entry.getValue()));
        }

        System.out.printf("\n=== СТАТИСТИКА ===%n");
        System.out.printf("Всего студентов: %d%n", allStudents.size());
        System.out.printf("Отличников: %d%n", studentsMap.size());
        System.out.printf("Всего экзаменов у отличников: %d%n", excellentStudents.size());
    }
}

class StudentExamInfo {
    String lastName;
    String firstName;
    String middleName;
    int course;
    String group;
    int sessionNumber;
    String subjectName;
    int grade;

    public StudentExamInfo(String lastName, String firstName, String middleName,
                           int course, String group, int sessionNumber,
                           String subjectName, int grade) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.sessionNumber = sessionNumber;
        this.subjectName = subjectName;
        this.grade = grade;
    }
}

class GradeBook {
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private String group;
    private List<Session> sessions;

    public GradeBook(String lastName, String firstName, String middleName,
                     int course, String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.sessions = new ArrayList<>();
    }

    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public int getCourse() { return course; }
    public String getGroup() { return group; }
    public List<Session> getSessions() { return sessions; }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public boolean isExcellentStudent() {
        for (Session session : sessions) {
            if (!session.allExamsExcellent() || !session.allCreditsPassed()) {
                return false;
            }
        }
        return true;
    }

    public class Session {
        private int sessionNumber;
        private List<Exam> exams;
        private List<Credit> credits;

        public Session(int sessionNumber) {
            this.sessionNumber = sessionNumber;
            this.exams = new ArrayList<>();
            this.credits = new ArrayList<>();
        }

        public int getSessionNumber() { return sessionNumber; }
        public List<Exam> getExams() { return exams; }
        public List<Credit> getCredits() { return credits; }

        public void addExam(Exam exam) {
            exams.add(exam);
        }

        public void addCredit(Credit credit) {
            credits.add(credit);
        }

        public boolean allExamsExcellent() {
            for (Exam exam : exams) {
                if (exam.getGrade() < 9) {
                    return false;
                }
            }
            return true;
        }

        public boolean allCreditsPassed() {
            for (Credit credit : credits) {
                if (!credit.isPassed()) {
                    return false;
                }
            }
            return true;
        }
    }

    public class Exam {
        private String subjectName;
        private int grade;

        public Exam(String subjectName, int grade) {
            this.subjectName = subjectName;
            this.grade = grade;
        }

        public String getSubjectName() { return subjectName; }
        public int getGrade() { return grade; }
    }

    public class Credit {
        private String subjectName;
        private boolean passed;

        public Credit(String subjectName, boolean passed) {
            this.subjectName = subjectName;
            this.passed = passed;
        }

        public String getSubjectName() { return subjectName; }
        public boolean isPassed() { return passed; }
    }
}