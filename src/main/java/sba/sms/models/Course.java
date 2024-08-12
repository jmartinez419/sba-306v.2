package sba.sms.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name="course")
/**
 * Course is a POJO, configured as a persistent class that represents (or maps to) a table
 * name 'course' in the database. A Course object contains fields that represent course
 * information and a mapping of 'courses' that indicate an inverse or referencing side
 * of the relationship. Implement Lombok annotations to eliminate boilerplate code.
 */
@AllArgsConstructor

@Getter
@Setter

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseId")
    public int courseId;
    @Column(name = "courseName", length = 50, nullable = false)
    public String courseName;
    @Column(name = "Instructor", length = 50, nullable = false)
    public String Instructor;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REMOVE, CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "courseSet")
    Set<Student> students = new LinkedHashSet<>();

    public Course(String courseName, String instructor) {
        this.courseName = courseName;
        Instructor = instructor;
    }

    public Course() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && Objects.equals(courseName, course.courseName) && Objects.equals(Instructor, course.Instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, Instructor);
    }

}



