package com.university;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class RecordBook {
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("course")
    private int course;

    @JsonProperty("group")
    private String group;

    @JsonProperty("sessions")
    private List<Session> sessions;

    // Конструктор по умолчанию для Jackson
    public RecordBook() {
        this.sessions = new ArrayList<>();
    }

    public RecordBook(String lastName, String firstName, String middleName,
                      int course, String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.sessions = new ArrayList<>();
    }

    public static class Session {
        @JsonProperty("sessionNumber")
        private int sessionNumber;

        @JsonProperty("exams")
        private List<Exam> exams;

        @JsonProperty("credits")
        private List<Credit> credits;

        public Session() {
            this.exams = new ArrayList<>();
            this.credits = new ArrayList<>();
        }

        public Session(int sessionNumber) {
            this.sessionNumber = sessionNumber;
            this.exams = new ArrayList<>();
            this.credits = new ArrayList<>();
        }

        public void addExam(String subject, int grade) {
            exams.add(new Exam(subject, grade));
        }

        public void addCredit(String subject, boolean passed) {
            credits.add(new Credit(subject, passed));
        }

        public boolean allExamsExcellent() {
            return exams.stream().allMatch(exam -> exam.getGrade() >= 9);
        }

        public boolean allCreditsPassed() {
            return credits.stream().allMatch(Credit::isPassed);
        }

        public int getSessionNumber() { return sessionNumber; }
        public void setSessionNumber(int sessionNumber) { this.sessionNumber = sessionNumber; }

        public List<Exam> getExams() { return exams; }
        public void setExams(List<Exam> exams) { this.exams = exams; }

        public List<Credit> getCredits() { return credits; }
        public void setCredits(List<Credit> credits) { this.credits = credits; }
    }

    public static class Exam {
        @JsonProperty("subject")
        private String subject;

        @JsonProperty("grade")
        private int grade;

        public Exam() {}

        public Exam(String subject, int grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public int getGrade() { return grade; }
        public void setGrade(int grade) { this.grade = grade; }
    }

    public static class Credit {
        @JsonProperty("subject")
        private String subject;

        @JsonProperty("passed")
        private boolean passed;

        public Credit() {}

        public Credit(String subject, boolean passed) {
            this.subject = subject;
            this.passed = passed;
        }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public boolean isPassed() { return passed; }
        public void setPassed(boolean passed) { this.passed = passed; }
    }

    public Session createSession(int sessionNumber) {
        Session session = new Session(sessionNumber);
        sessions.add(session);
        return session;
    }

    public boolean isExcellentStudent() {
        return sessions.stream().allMatch(session ->
                session.allExamsExcellent() && session.allCreditsPassed());
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public int getCourse() { return course; }
    public void setCourse(int course) { this.course = course; }

    public String getGroup() { return group; }
    public void setGroup(String group) { this.group = group; }

    public List<Session> getSessions() { return sessions; }
    public void setSessions(List<Session> sessions) { this.sessions = sessions; }

    @Override
    public String toString() {
        return String.format("%s %s %s, курс: %d, группа: %s",
                lastName, firstName, middleName, course, group);
    }
}