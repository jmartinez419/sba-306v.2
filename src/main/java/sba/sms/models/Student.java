package sba.sms.models;
import sba.sms.models.Course;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.util.HashSet;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Student {

@Id
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "password", length = 50, nullable = false)
    private String password;
    @Column(name="name",length = 50, nullable = false)
    public String name;

    @ManyToMany (cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name="student_courses",
            joinColumns = @JoinColumn(name = "student_email"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )

    @Column(name="courses", length = 50, nullable = false)
    Set<Course> courseSet = new LinkedHashSet<>();


    public Student(String mail, String name, String password) {
        this.email = mail;
        this.name = name;
        this.password = password;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email) && Objects.equals(password, student.password) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, name);
    }
    public void registerCourseHelper (Course courses){
        courseSet.add(courses);
        courses.getStudents().add(this);
    }
}



