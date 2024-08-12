package sba.sms.models;
import sba.sms.models.Course;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;



import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
/**
 * Student is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'student' in the database. A Student object contains fields that represent student
 * login credentials and a join table containing a registered student's email and course(s)
 * data. The Student class can be viewed as the owner of the bi-directional relationship.
 * Implement Lombok annotations to eliminate boilerplate code.
 */
@Entity
@Table(name="student")
@Getter
@Setter

public class Student {

    @ManyToMany (cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
            @JoinTable(
                    name="student_courses",
                    joinColumns = @JoinColumn(name = "student_email"),
                    inverseJoinColumns = @JoinColumn(name = "courses_id")
            )
    Set<Course> likedCourses;
    Set<Course> courseSet = new LinkedHashSet<>();
@Id
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "password", length = 50, nullable = false)
    private String password;
    @Column(name="name",length = 50, nullable = false)
    public String name;
    @Column(name="courses", length = 50, nullable = false)
    Set<Course> courses;

    public Student(Set<Course> likedCourses, Set<Course> courseSet, String email, String password, String name, Set<Course> courses) {
        this.likedCourses = likedCourses;
        this.courseSet = courseSet;
        this.email = email;
        this.password = password;
        this.name = name;
        this.courses = courses;
    }

    public Student(String mail, String name, String password) {
        this.email = mail;
        this.name = name;
        this.password = password;

    }

    public Student() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(likedCourses, student.likedCourses) && Objects.equals(email, student.email) && Objects.equals(password, student.password) && Objects.equals(name, student.name) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likedCourses, email, password, name, courses);
    }
    public void registerCourseHelper (Course courses){
        courseSet.add(courses);
        courses.getStudent().add(this);
    }
}



