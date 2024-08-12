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
@Getter
@Setter

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @ManyToMany (cascade = {CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REMOVE,CascadeType.PERSIST}, fetch = FetchType.EAGER, mappedBy = "courses")
    Set<Course> likedCourses;

    public int courseId;
    public String courseName;
    public String Instructor;
    Set<Student> students;

    public Course(Set<Course> likedCourses, int courseId, String courseName, String instructor, Set<Student> students, Set<Student> student) {
        this.likedCourses = likedCourses;
        this.courseId = courseId;
        this.courseName = courseName;
        Instructor = instructor;
        this.students = students;
        this.student = student;
    }

    public Course(String courseName, String instructor) {
        this.courseName = courseName;
        Instructor = instructor;
    }

    @ManyToMany(targetEntity = Student.class)
    private Set<Student> student;

    public Course() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && Objects.equals(likedCourses, course.likedCourses) && Objects.equals(courseName, course.courseName) && Objects.equals(Instructor, course.Instructor) && Objects.equals(students, course.students) && Objects.equals(student, course.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likedCourses, courseId, courseName, Instructor, students, student);
    }

    public Object getId() {
        return null;
    }

    public Object Name() {
        return null;
    }

    public Object getName() {
        return null;
    }
}



