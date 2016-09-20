package andrewpolvoko.htp_android_courses;

public class Student implements Comparable {
    String id;
    String firstName;
    String lastName;

    public Student(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int compareTo(Object another) {
        if (another instanceof Student) {
            return firstName.compareToIgnoreCase(((Student) another).firstName);
        } else
            return -1;
    }
}
