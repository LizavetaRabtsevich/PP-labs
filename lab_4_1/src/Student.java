import java.util.Objects;

public class Student {
    public long num;
    public String name;
    public int group;
    public double grade;

    public Student(long num, String name, int group, double grade) {
        this.num = num;
        this.name = name;
        this.group = group;
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return num == s.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }

    @Override
    public String toString() {
        return num + "," + name + "," + group + "," + grade;
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        return new Student(
                Long.parseLong(parts[0]),
                parts[1],
                Integer.parseInt(parts[2]),
                Double.parseDouble(parts[3])
        );
    }
}
