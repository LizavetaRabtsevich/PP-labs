import java.util.ArrayList;
import java.util.List;

public class RecordBook {
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private String group;

    private List<Session> sessions;

    public RecordBook(String lastName, String firstName, String middleName,
                      int course, String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.sessions = new ArrayList<>();
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

        public void addExam(String subject, int grade) {
            exams.add(new Exam(subject, grade));
        }

        public void addCredit(String subject, boolean passed) {
            credits.add(new Credit(subject, passed));
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

        public int getSessionNumber() {
            return sessionNumber;
        }

        public List<Exam> getExams() {
            return exams;
        }

        public List<Credit> getCredits() {
            return credits;
        }
    }

    public class Exam {
        private String subject;
        private int grade;

        public Exam(String subject, int grade) {
            this.subject = subject;
            this.grade = grade;
        }

        public String getSubject() {
            return subject;
        }

        public int getGrade() {
            return grade;
        }
    }

    public class Credit {
        private String subject;
        private boolean passed;

        public Credit(String subject, boolean passed) {
            this.subject = subject;
            this.passed = passed;
        }

        public String getSubject() {
            return subject;
        }

        public boolean isPassed() {
            return passed;
        }
    }

    public Session createSession(int sessionNumber) {
        Session session = new Session(sessionNumber);
        sessions.add(session);
        return session;
    }

    public boolean isExcellentStudent() {
        for (Session session : sessions) {
            if (!session.allExamsExcellent() || !session.allCreditsPassed()) {
                return false;
            }
        }
        return true;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getCourse() {
        return course;
    }

    public String getGroup() {
        return group;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s, курс: %d, группа: %s",
                lastName, firstName, middleName, course, group);
    }
}