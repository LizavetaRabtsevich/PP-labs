import java.util.ArrayList;
import java.util.List;

public class GradeBook {
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
            return exams.stream().allMatch(exam -> exam.getGrade() >= 9);
        }

        public boolean allCreditsPassed() {
            return credits.stream().allMatch(Credit::isPassed);
        }
    }

    public class Exam {
        private String subjectName;
        private int grade; // оценка от 1 до 10

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

    public boolean isExcellentStudent() {
        return sessions.stream().allMatch(session ->
                session.allExamsExcellent() && session.allCreditsPassed());
    }

    public List<ExamInfo> getExcellentExams() {
        List<ExamInfo> excellentExams = new ArrayList<>();
        if (isExcellentStudent()) {
            for (Session session : sessions) {
                for (Exam exam : session.getExams()) {
                    excellentExams.add(new ExamInfo(
                            lastName, firstName, middleName,
                            course, group,
                            session.getSessionNumber(),
                            exam.getSubjectName(),
                            exam.getGrade()
                    ));
                }
            }
        }
        return excellentExams;
    }

    public static class ExamInfo {
        private String lastName;
        private String firstName;
        private String middleName;
        private int course;
        private String group;
        private int sessionNumber;
        private String subjectName;
        private int grade;

        public ExamInfo(String lastName, String firstName, String middleName,
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

        public String getLastName() { return lastName; }
        public String getFirstName() { return firstName; }
        public String getMiddleName() { return middleName; }
        public int getCourse() { return course; }
        public String getGroup() { return group; }
        public int getSessionNumber() { return sessionNumber; }
        public String getSubjectName() { return subjectName; }
        public int getGrade() { return grade; }
    }
}