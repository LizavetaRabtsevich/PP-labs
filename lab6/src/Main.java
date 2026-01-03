import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

interface LearningStrategy {
    void learn(String name);
}

class SchoolLearning implements LearningStrategy {
    @Override
    public void learn(String name) {
        System.out.println(name + " учится в школе: посещает уроки и делает домашние задания");
    }
}

class UniversityLearning implements LearningStrategy {
    @Override
    public void learn(String name) {
        System.out.println(name + " учится в университете: слушает лекции и сдаёт экзамены");
    }
}

class MasterLearning implements LearningStrategy {
    @Override
    public void learn(String name) {
        System.out.println(name + " учится в магистратуре: проводит исследования и пишет диссертацию");
    }
}

abstract class Student {
    protected String name;
    protected int age;
    protected LearningStrategy learningStrategy;
    protected List<String> features;
    protected double averageGrade;
    protected boolean hasScholarship;
    protected int course;

    protected Student(StudentBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.learningStrategy = builder.learningStrategy;
        this.features = builder.features;
        this.averageGrade = builder.averageGrade;
        this.hasScholarship = builder.hasScholarship;
        this.course = builder.course;
    }

    public abstract void study();
    public abstract void takeExams();
    public abstract void displayInfo();

    public void useFeature(String feature) {
        if (features.contains(feature)) {
            System.out.println(name + " использует: " + feature);
        } else {
            System.out.println(name + " нет доступа к: " + feature);
        }
    }

    public void improveGrade() {
        if (averageGrade < 5.0) {
            averageGrade += 0.1;
            System.out.println(name + " улучшил средний балл до: " + String.format("%.1f", averageGrade));
        }
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getAverageGrade() { return averageGrade; }
    public boolean hasScholarship() { return hasScholarship; }
    public List<String> getFeatures() { return new ArrayList<>(features); }
}

class SchoolStudent extends Student {
    private String school;
    private int classLevel;
    private boolean hasExtendedProgram;

    private SchoolStudent(SchoolStudentBuilder builder) {
        super(builder);
        this.school = builder.school;
        this.classLevel = builder.classLevel;
        this.hasExtendedProgram = builder.hasExtendedProgram;
    }

    @Override
    public void study() {
        learningStrategy.learn(name);
        if (hasExtendedProgram) {
            System.out.println(name + " учится по расширенной программе");
        }
    }

    @Override
    public void takeExams() {
        System.out.println(name + " сдает ВПР в " + classLevel + " классе");
    }

    @Override
    public void displayInfo() {
        System.out.println("Информация о школьнике:");
        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age);
        System.out.println("Школа: " + school);
        System.out.println("Класс: " + classLevel);
        System.out.println("Средний балл: " + averageGrade);
        System.out.println("Стипендия: " + (hasScholarship ? "да" : "нет"));
        System.out.println("Расширенная программа: " + (hasExtendedProgram ? "да" : "нет"));
        System.out.println("Особенности: " + (features.isEmpty() ? "нет" : String.join(", ", features)));
    }

    public void participateInOlympiad() {
        if (features.contains("Олимпиады")) {
            System.out.println(name + " участвует в олимпиаде");
        }
    }

    public static class SchoolStudentBuilder extends StudentBuilder {
        private String school;
        private int classLevel;
        private boolean hasExtendedProgram = false;

        public SchoolStudentBuilder(String name, int age) {
            super(name, age);
            this.learningStrategy = new SchoolLearning();
        }

        public SchoolStudentBuilder withSchool(String school) {
            this.school = school;
            return this;
        }

        public SchoolStudentBuilder withClassLevel(int classLevel) {
            this.classLevel = classLevel;
            return this;
        }

        public SchoolStudentBuilder withExtendedProgram() {
            this.hasExtendedProgram = true;
            return this;
        }

        @Override
        public SchoolStudent build() {
            return new SchoolStudent(this);
        }
    }
}

class UniversityStudent extends Student {
    private String university;
    private String specialty;
    private boolean livesInDormitory;

    private UniversityStudent(UniversityStudentBuilder builder) {
        super(builder);
        this.university = builder.university;
        this.specialty = builder.specialty;
        this.livesInDormitory = builder.livesInDormitory;
    }

    @Override
    public void study() {
        learningStrategy.learn(name);
        if (livesInDormitory) {
            System.out.println(name + " живет в общежитии");
        }
    }

    @Override
    public void takeExams() {
        System.out.println(name + " сдает сессию на " + course + " курсе");
        if (averageGrade > 4.5) {
            System.out.println(name + " сдает на 'отлично'!");
        }
    }

    @Override
    public void displayInfo() {
        System.out.println("Информация о студенте:");
        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age);
        System.out.println("Университет: " + university);
        System.out.println("Специальность: " + specialty);
        System.out.println("Курс: " + course);
        System.out.println("Средний балл: " + averageGrade);
        System.out.println("Стипендия: " + (hasScholarship ? "да" : "нет"));
        System.out.println("Общежитие: " + (livesInDormitory ? "да" : "нет"));
        System.out.println("Особенности: " + (features.isEmpty() ? "нет" : String.join(", ", features)));
    }

    public void participateInConference() {
        if (features.contains("Конференции")) {
            System.out.println(name + " выступает на научной конференции");
        }
    }

    public static class UniversityStudentBuilder extends StudentBuilder {
        private String university;
        private String specialty;
        private boolean livesInDormitory = false;

        public UniversityStudentBuilder(String name, int age) {
            super(name, age);
            this.learningStrategy = new UniversityLearning();
        }

        public UniversityStudentBuilder withUniversity(String university) {
            this.university = university;
            return this;
        }

        public UniversityStudentBuilder withSpecialty(String specialty) {
            this.specialty = specialty;
            return this;
        }

        public UniversityStudentBuilder withDormitory() {
            this.livesInDormitory = true;
            return this;
        }

        @Override
        public UniversityStudent build() {
            return new UniversityStudent(this);
        }
    }
}

class MasterStudent extends Student {
    private String researchTopic;
    private String supervisor;
    private boolean hasPublication;

    private MasterStudent(MasterStudentBuilder builder) {
        super(builder);
        this.researchTopic = builder.researchTopic;
        this.supervisor = builder.supervisor;
        this.hasPublication = builder.hasPublication;
    }

    @Override
    public void study() {
        learningStrategy.learn(name);
        if (hasPublication) {
            System.out.println(name + " имеет научные публикации");
        }
    }

    @Override
    public void takeExams() {
        System.out.println(name + " защищает научный проект по теме: " + researchTopic);
    }

    @Override
    public void displayInfo() {
        System.out.println("Информация о магистранте:");
        System.out.println("Имя: " + name);
        System.out.println("Возраст: " + age);
        System.out.println("Тема исследования: " + researchTopic);
        System.out.println("Научный руководитель: " + supervisor);
        System.out.println("Средний балл: " + averageGrade);
        System.out.println("Стипендия: " + (hasScholarship ? "да" : "нет"));
        System.out.println("Публикации: " + (hasPublication ? "да" : "нет"));
        System.out.println("Особенности: " + (features.isEmpty() ? "нет" : String.join(", ", features)));
    }

    public void conductResearch() {
        if (features.contains("Исследования")) {
            System.out.println(name + " проводит научное исследование");
        }
    }

    public static class MasterStudentBuilder extends StudentBuilder {
        private String researchTopic;
        private String supervisor;
        private boolean hasPublication = false;

        public MasterStudentBuilder(String name, int age) {
            super(name, age);
            this.learningStrategy = new MasterLearning();
        }

        public MasterStudentBuilder withResearchTopic(String topic) {
            this.researchTopic = topic;
            return this;
        }

        public MasterStudentBuilder withSupervisor(String supervisor) {
            this.supervisor = supervisor;
            return this;
        }

        public MasterStudentBuilder withPublication() {
            this.hasPublication = true;
            return this;
        }

        @Override
        public MasterStudent build() {
            return new MasterStudent(this);
        }
    }
}

abstract class StudentBuilder {
    protected String name;
    protected int age;
    protected LearningStrategy learningStrategy;
    protected List<String> features = new ArrayList<>();
    protected double averageGrade = 4.0;
    protected boolean hasScholarship = false;
    protected int course = 1;

    public StudentBuilder(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public StudentBuilder withAverageGrade(double grade) {
        this.averageGrade = grade;
        return this;
    }

    public StudentBuilder withScholarship() {
        this.hasScholarship = true;
        return this;
    }

    public StudentBuilder withCourse(int course) {
        this.course = course;
        return this;
    }

    public StudentBuilder addFeature(String feature) {
        this.features.add(feature);
        return this;
    }

    public abstract Student build();
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Система создания учащихся");

        while (true) {
            System.out.println("Главное меню:");
            System.out.println("1. Создать школьника");
            System.out.println("2. Создать студента");
            System.out.println("3. Создать магистранта");
            System.out.println("4. Показать всех учащихся");
            System.out.println("5. Выполнить действия");
            System.out.println("6. Выйти из программы");
            System.out.print("Выберите действие: ");

            int choice = getIntInput();

            switch (choice) {
                case 1:
                    students.add(createSchoolStudent());
                    break;
                case 2:
                    students.add(createUniversityStudent());
                    break;
                case 3:
                    students.add(createMasterStudent());
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    performActions();
                    break;
                case 6:
                    System.out.println("До свидания!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Неверный выбор, попробуйте снова");
            }
        }
    }

    private static SchoolStudent createSchoolStudent() {
        System.out.println("Создание школьника");

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст: ");
        int age = getIntInput();

        System.out.print("Введите школу: ");
        String school = scanner.nextLine();

        System.out.print("Введите класс: ");
        int classLevel = getIntInput();

        SchoolStudent.SchoolStudentBuilder builder = new SchoolStudent.SchoolStudentBuilder(name, age)
                .withSchool(school)
                .withClassLevel(classLevel);

        if (askYesNo("Расширенная программа обучения?")) {
            builder.withExtendedProgram();
        }

        if (askYesNo("Стипендия?")) {
            builder.withScholarship();
        }

        System.out.print("Введите средний балл: ");
        builder.withAverageGrade(getDoubleInput());

        addFeaturesToBuilder(builder);

        SchoolStudent student = builder.build();
        System.out.println("Школьник создан!");
        return student;
    }

    private static UniversityStudent createUniversityStudent() {
        System.out.println("Создание студента");

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст: ");
        int age = getIntInput();

        System.out.print("Введите университет: ");
        String university = scanner.nextLine();

        System.out.print("Введите специальность: ");
        String specialty = scanner.nextLine();

        System.out.print("Введите курс: ");
        int course = getIntInput();

        UniversityStudent.UniversityStudentBuilder builder = new UniversityStudent.UniversityStudentBuilder(name, age)
                .withUniversity(university)
                .withSpecialty(specialty);

        if (askYesNo("Проживает в общежитии?")) {
            builder.withDormitory();
        }

        if (askYesNo("Стипендия?")) {
            builder.withScholarship();
        }

        System.out.print("Введите средний балл: ");
        builder.withAverageGrade(getDoubleInput());

        addFeaturesToBuilder(builder);

        UniversityStudent student = builder.build();
        System.out.println("Студент создан!");
        return student;
    }

    private static MasterStudent createMasterStudent() {
        System.out.println("Создание магистранта");

        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите возраст: ");
        int age = getIntInput();

        System.out.print("Введите тему исследования: ");
        String researchTopic = scanner.nextLine();

        System.out.print("Введите научного руководителя: ");
        String supervisor = scanner.nextLine();

        MasterStudent.MasterStudentBuilder builder = new MasterStudent.MasterStudentBuilder(name, age)
                .withResearchTopic(researchTopic)
                .withSupervisor(supervisor);

        if (askYesNo("Научные публикации?")) {
            builder.withPublication();
        }

        if (askYesNo("Стипендия?")) {
            builder.withScholarship();
        }

        System.out.print("Введите средний балл: ");
        builder.withAverageGrade(getDoubleInput());

        addFeaturesToBuilder(builder);

        MasterStudent student = builder.build();
        System.out.println("Магистрант создан!");
        return student;
    }

    private static void addFeaturesToBuilder(StudentBuilder builder) {
        System.out.println("Добавление особенностей:");
        String[] availableFeatures = {"Олимпиады", "Конференции", "Исследования", "Спорт", "Волонтерство", "Стажировка"};

        for (String feature : availableFeatures) {
            if (askYesNo("Добавить '" + feature + "'?")) {
                builder.addFeature(feature);
            }
        }
    }

    private static void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Нет созданных учащихся");
            return;
        }

        System.out.println("Все учащиеся:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName() +
                    " (" + students.get(i).getClass().getSimpleName() + ")");
        }

        System.out.print("Показать подробную информацию? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            for (Student student : students) {
                student.displayInfo();
                System.out.println();
            }
        }
    }

    private static void performActions() {
        if (students.isEmpty()) {
            System.out.println("Нет созданных учащихся");
            return;
        }

        System.out.println("Выберите учащегося:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i).getName());
        }

        System.out.print("Выберите номер: ");
        int index = getIntInput() - 1;

        if (index < 0 || index >= students.size()) {
            System.out.println("Неверный номер");
            return;
        }

        Student student = students.get(index);

        System.out.println("Действия для " + student.getName() + ":");
        System.out.println("1. Учиться");
        System.out.println("2. Сдать экзамены");
        System.out.println("3. Использовать особенность");
        System.out.println("4. Улучшить оценку");
        System.out.println("5. Специальное действие");

        int action = getIntInput();

        switch (action) {
            case 1:
                student.study();
                break;
            case 2:
                student.takeExams();
                break;
            case 3:
                useFeature(student);
                break;
            case 4:
                student.improveGrade();
                break;
            case 5:
                specialAction(student);
                break;
            default:
                System.out.println("Неверное действие");
        }
    }

    private static void useFeature(Student student) {
        List<String> features = student.getFeatures();
        if (features.isEmpty()) {
            System.out.println("Нет доступных особенностей");
            return;
        }

        System.out.println("Доступные особенности:");
        for (int i = 0; i < features.size(); i++) {
            System.out.println((i + 1) + ". " + features.get(i));
        }

        System.out.print("Выберите особенность: ");
        int featureIndex = getIntInput() - 1;

        if (featureIndex >= 0 && featureIndex < features.size()) {
            student.useFeature(features.get(featureIndex));
        } else {
            System.out.println("Неверный номер");
        }
    }

    private static void specialAction(Student student) {
        if (student instanceof SchoolStudent) {
            ((SchoolStudent) student).participateInOlympiad();
        } else if (student instanceof UniversityStudent) {
            ((UniversityStudent) student).participateInConference();
        } else if (student instanceof MasterStudent) {
            ((MasterStudent) student).conductResearch();
        }
    }

    private static boolean askYesNo(String question) {
        System.out.print(question + " (y/n): ");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    private static int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Введите целое число: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
            }
        }
    }
}